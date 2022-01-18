package com.example.server;

import com.example.server.model.Game;
import com.example.server.model.GameState;
import com.example.server.model.Question;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.tcprnament.shared.commands.client.ClientCommand;
import org.example.tcprnament.shared.commands.client.ClientCommandParser;
import org.example.tcprnament.shared.commands.client.concrete.CreateGameCommand;
import org.example.tcprnament.shared.commands.client.concrete.JoinGameCommand;
import org.example.tcprnament.shared.commands.client.concrete.QuestionAnswerCommand;
import org.example.tcprnament.shared.commands.client.concrete.SetNickCommand;
import org.example.tcprnament.shared.commands.server.ServerCommand;
import org.example.tcprnament.shared.commands.server.ServerCommandType;
import org.example.tcprnament.shared.commands.server.concrete.*;
import org.springframework.context.annotation.DependsOn;
import org.springframework.integration.ip.tcp.connection.AbstractServerConnectionFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@DependsOn("gateway")
@RequiredArgsConstructor
@Getter
public class TournamentProtocolServerApplication extends ClientCommandParser {

    private final AbstractServerConnectionFactory connectionFactory;
    private final Gateway gateway;
    private final QuestionsProviderService questionsProviderService;

    private final Map<String, Game> currentGames = new ConcurrentHashMap<>();
    private final Map<String, String> gameOwnershipMap = new ConcurrentHashMap<>();
    private final Map<String, Game> playerGameMap = new ConcurrentHashMap<>();
    private final Map<String, String> playerNick = new ConcurrentHashMap<>();

    private final ThreadFactory gameThreadFactory = new ThreadFactoryBuilder().setNameFormat("Game-thread-%d").build();
    private final ScheduledExecutorService gameExecutor = Executors.newScheduledThreadPool(10, gameThreadFactory);

    @Override
    protected void onSetNick(SetNickCommand command) {
        playerNick.put(command.getConnectionId(),command.getNick());
    }

    @Override
    protected void onCreateGame(CreateGameCommand command) {
        if (!currentGames.containsKey(command.getName())) {
            Game newGame = new Game(command.getConnectionId(), command.getName(), command.getPassword());
            newGame.getPlayers().put(command.getConnectionId(), 0);

            currentGames.put(command.getName(), newGame);
            gameOwnershipMap.put(command.getConnectionId(), command.getName());
            playerGameMap.put(command.getConnectionId(), newGame);

            log.info("Created game with name {}", command.getName());
            //setPlayersWrongTableCounter and lastAns
            newGame.getPlayers().forEach((key, value) -> newGame.setWrongAnsCounter(key, 0));
            newGame.getPlayers().forEach((key, value) -> newGame.setLastAns(key, -1));
            gateway.send(serialize(new GameCreatedCommand(command.getName())), command.getConnectionId());
        } else {
            sendReject(command, "Game with name " + command.getName() + " already exists");
        }
    }

    @Override
    protected void onShowGames(ClientCommand command) {
        List<String> gamesNames = currentGames.values().stream().map(Game::getName).collect(Collectors.toList());
        gateway.send(serialize(new GamesListCommand(gamesNames)), command.getConnectionId());
    }

    @Override
    protected void onJoinGame(JoinGameCommand command) {
        if (currentGames.containsKey(command.getName())) {
            Game selectedGame = currentGames.get(command.getName());
            if (selectedGame.getCurrentState() == GameState.LOBBY) {

                if (!selectedGame.getPassword().equals(command.getPassword())) {
                    sendReject(command, "Wrong password");
                }

                log.info("Player {}, joined game {}", command.getConnectionId(), selectedGame.getName());
                currentGames.get(command.getName()).getPlayers().put(command.getConnectionId(), 0);
                playerGameMap.put(command.getConnectionId(), selectedGame);

                gateway.send(serialize(new GameJoinedCommand(command.getName())), command.getConnectionId());

                selectedGame.getPlayers().forEach((player, score) -> {
                    gateway.send(serialize(new PlayerJoinedCommand(playerNick.get(command.getConnectionId()))), player);
                });
            } else {
                sendReject(command, "Game already started");
            }
        } else {
            sendReject(command, "Cannot find game " + command.getName());
        }
    }

    @Override
    protected void onStartGame(ClientCommand command) {
        if (gameOwnershipMap.containsKey(command.getConnectionId())) {
            String gameName = gameOwnershipMap.get(command.getConnectionId());
            Game requestedGame = currentGames.get(gameName);

            requestedGame.getPlayers().forEach((player, score) -> {
                gateway.send(serialize(new ServerCommand(ServerCommandType.GAME_STARTED)), player);
            });

            log.info("Starting game: {}", gameName);
            requestedGame.setGameQuestions(questionsProviderService.pickNRandomQuestions(10));
            GameDealer dealer = new GameDealer(this, requestedGame);
            gameExecutor.execute(dealer);

        } else {
            sendReject(command, "User is not a game owner");
        }
    }

    @Override
    protected void onLeaveGame(ClientCommand command) {
        for (Game g : currentGames.values()) {
            if (g.getPlayers().containsKey(command.getConnectionId())) {
                g.removePlayer(command.getConnectionId());
                playerGameMap.remove(command.getConnectionId());

                log.info("Player : {}, left game : {}", command.getConnectionId(), g.getName());

                g.getPlayers().forEach((player, score) -> {
                    gateway.send(serialize(new PlayerLeftCommand(playerNick.get(command.getConnectionId()))), player);
                });
                return;
            }
        }

        sendReject(command, "Player not in game");
    }

    @Override
    protected void onQuestionAnswer(QuestionAnswerCommand command) {
        if (playerGameMap.containsKey(command.getConnectionId())) {
            Game playerGame = playerGameMap.get(command.getConnectionId());
            Question question = playerGame.getGameQuestions().get(command.getQuestionNumber() - 1);

            int wrongAnsCounter = playerGame.getWrongAnsCounter().get(command.getConnectionId());
            boolean isOldAns = playerGame.getLastAns().get(command.getConnectionId()).equals(command.getQuestionNumber());

            if (isOldAns) {
                log.info(
                        "[Game : {} player : {}] --> already answered for question number {}",
                        playerGame.getName(),
                        command.getConnectionId(),
                        command.getQuestionNumber()
                );
                sendReject(command, "Already answered for question number " + command.getQuestionNumber());
            }else if (command.getAnswer().equals(question.getCorrectAnswer()) && wrongAnsCounter < 3 ) {
                log.info(
                        "[Game : {} player : {}] --> correct answer for question number: {}! +1 point",
                        playerGame.getName(),
                        command.getConnectionId(),
                        command.getQuestionNumber()
                );
                Integer previous = playerGame.getPlayers().get(command.getConnectionId());
                playerGame.getPlayers().put(command.getConnectionId(), previous + 1);
                playerGame.setWrongAnsCounter(command.getConnectionId(), 0);
                playerGame.setLastAns(command.getConnectionId(), command.getQuestionNumber());
            } else if(wrongAnsCounter < 3) {
                log.info(
                        "[Game : {} player : {}] --> wrong answer for question number: {}! -2 points",
                        playerGame.getName(),
                        command.getConnectionId(),
                        command.getQuestionNumber()
                );

                Integer previous = playerGame.getPlayers().get(command.getConnectionId());
                playerGame.getPlayers().put(command.getConnectionId(), previous - 2);
                playerGame.setWrongAnsCounter(command.getConnectionId(), wrongAnsCounter+1);
                playerGame.setLastAns(command.getConnectionId(), command.getQuestionNumber());
            }else{
                log.info(
                        "[Game : {} player : {}] --> ban from answering for question number: {}! due to 3 wrong ans before.",
                        playerGame.getName(),
                        command.getConnectionId(),
                        command.getQuestionNumber()
                );
                sendReject(command, "Player has been banned for wrong answers!");
                playerGame.setWrongAnsCounter(command.getConnectionId(), 0);
            }

        } else {
            sendReject(command, "Player not in any game");
        }
    }

    private void sendReject(ClientCommand command, String reason) {
        log.info(
                "Sending reject to client {} -> [refId: {}, refType : {}, reason: {}]",
                command.getConnectionId(),
                command.getId(),
                command.getType(),
                reason
        );
        gateway.send(serialize(new CommandRejected(command.getId(), command.getType(), reason)), command.getConnectionId());
    }
}
