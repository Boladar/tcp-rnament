package com.example.server;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.tcprnament.shared.commands.client.ClientCommand;
import org.example.tcprnament.shared.commands.client.ClientCommandParser;
import org.example.tcprnament.shared.commands.client.concrete.CreateGameCommand;
import org.example.tcprnament.shared.commands.client.concrete.JoinGameCommand;
import org.example.tcprnament.shared.commands.server.ServerCommand;
import org.example.tcprnament.shared.commands.server.ServerCommandType;
import org.example.tcprnament.shared.commands.server.concrete.*;
import org.springframework.context.annotation.DependsOn;
import org.springframework.integration.ip.tcp.connection.AbstractServerConnectionFactory;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
@DependsOn("gateway")
@RequiredArgsConstructor
public class TournamentProtocolServerApplication extends ClientCommandParser {

    private final AbstractServerConnectionFactory connectionFactory;
    private final Gateway gateway;

    private final Map<String, Game> currentGames = new ConcurrentHashMap<>();
    private final Map<String, String> gameOwnershipMap = new ConcurrentHashMap<>();

    @Override
    protected void onCreateGame(CreateGameCommand command) {
        if (!currentGames.containsKey(command.getName())) {
            Game newGame = new Game(command.getConnectionId(), command.getName(), command.getPassword());
            newGame.getPlayers().put(command.getConnectionId(), 0);

            currentGames.put(command.getName(), newGame);
            gameOwnershipMap.put(command.getConnectionId(), command.getName());
            log.info("Created game with name {}", command.getName());
            gateway.send(serialize(new GameCreatedCommand(command.getName())), command.getConnectionId());
        } else {
            sendReject(command, "Game with name " + command.getName() + " already exists");
        }
    }

    @Override
    protected void onShowGames(ClientCommand command) {
        sendReject(command, "Not yet implemented");
    }

    @Override
    protected void onJoinGame(JoinGameCommand command) {
        if (currentGames.containsKey(command.getName())) {
            Game selectedGame = currentGames.get(command.getName());
            if (!selectedGame.getPassword().equals(command.getPassword())) {
                sendReject(command, "Wrong password");
            }

            currentGames.get(command.getName()).getPlayers().put(command.getConnectionId(), 0);
            gateway.send(serialize(new GameJoinedCommand(command.getName())), command.getConnectionId());
        } else {
            sendReject(command, "Cannot find game " + command.getName());
        }
    }

    @Override
    protected void onStartGame(ClientCommand command) {
        if (gameOwnershipMap.containsKey(command.getConnectionId())) {
            String gameName = gameOwnershipMap.get(command.getConnectionId());
            currentGames.get(gameName).getPlayers().forEach((player, score) -> {
                gateway.send(serialize(new ServerCommand(ServerCommandType.GAME_STARTED)), command.getConnectionId());
            });
        } else {
            sendReject(command, "User is not a game owner");
        }
    }

    @Override
    protected void onLeaveGame(ClientCommand command) {
        for (Game g : currentGames.values()) {
            if (g.getPlayers().containsKey(command.getConnectionId())) {
                g.removePlayer(command.getConnectionId());
                g.getPlayers().forEach((player, score) -> {
                    gateway.send(serialize(new PlayerLeftCommand(command.getConnectionId())), player);
                });
                return;
            }
        }

        sendReject(command,"Player not in game");
    }

    private void sendReject(ClientCommand command, String reason) {
        gateway.send(serialize(new CommandRejected(command.getId(), command.getType(), reason)), command.getConnectionId());
    }
}
