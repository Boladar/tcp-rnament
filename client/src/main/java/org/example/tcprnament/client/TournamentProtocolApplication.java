package org.example.tcprnament.client;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.example.tcprnament.shared.commands.server.ServerCommand;
import org.example.tcprnament.shared.commands.server.ServerCommandParser;
import org.example.tcprnament.shared.commands.server.concrete.*;

import java.net.Socket;

@Slf4j
@Getter
@Setter
public class TournamentProtocolApplication extends ServerCommandParser {
    private Socket clientSocket;
    private NewGUI gui;

    public TournamentProtocolApplication(Socket clientSocket, NewGUI gui){
        this.clientSocket = clientSocket;
        this.gui = gui;
    }



    @Override
    protected void onGameCreated(GameCreatedCommand command) {
        log.info("Game: " + command.getName() + " created.");
        //wrzucic do pokoju ktory sie stworzylo
    }

    @Override
    protected void onGamesList(GamesListCommand command) {
        log.info("Received " + command.getGames().size() + " games list.");
        gui.gameListReceived(command.getGames());
    }

    @Override
    protected void onGameJoined(GameJoinedCommand command) {
        log.info("Player joined " + command.getName() + " game.");
        gui.gameJoined();
    }

    @Override
    protected void onGameStarted(ServerCommand command) {
        log.info("Game started.");
    }

    @Override
    protected void onCommandRejected(CommandRejected command) {
        log.info("Last command rejected! Reason: " + command.getReason());
        gui.rejectedInfoMessage(command.getReason());
    }

    @Override
    protected void onPlayerJoined(PlayerJoinedCommand command) {
        log.info("Player " + command.getPlayerName() + " joined lobby.");
        gui.addPlayer(command.getPlayerName());
    }

    @Override
    protected void onPlayerLeft(PlayerLeftCommand command) {
        log.info("Player " + command.getPlayerName() + " left lobby.");
        gui.removePlayer(command.getPlayerName());
    }

    @Override
    protected void onGameScoreUpdate(GameScoreUpdateCommand command) {
        log.info("Received new gameScore: " + command.getScoreboard());
        if(command.isFinished()){
            log.info("Game ended");
            gui.gameFinished();
        }
        gui.updateScoreBoard(command.getScoreboard());
    }

    @Override
    protected void onGameQuestion(GameQuestionCommand command) {
        log.info("New question: " + command.getQuestionNumber() + ". " + command.getText());
        gui.newQuestion(command.getQuestionNumber(), command.getText());
    }
}
