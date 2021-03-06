package org.example.tcprnament.client;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.example.tcprnament.shared.commands.client.ClientCommandType;
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
//        Vector<Vector<String>> games = new Vector(command.getGames());
//        Vector column = new Vector();
//        column.add("Aktywne gry");
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
        gui.gameStarted();
    }

    @Override
    protected void onCommandRejected(CommandRejected command) {
        log.info("Last command rejected! Reason: " + command.getReason());
        gui.rejectedInfoMessage(command.getReason());
        if(command.getRefCommandType()== ClientCommandType.JOIN_GAME){
            gui.joinGameDenied();
        }
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
        log.info("Received new gameScore: {}", command.getScoreboard());

        StringBuilder builder = new StringBuilder();
        builder.append("Aktualne wyniki: ");
        for(String player : command.getScoreboard().keySet()){
            builder.append(player  + ": " + command.getScoreboard().get(player) + " ");
        }

        if(command.isFinished()){
            log.info("Game ended");
            gui.gameFinished(builder.toString());
        }else{
            gui.updateScoreBoard(builder.toString());
        }
    }


    @Override
    protected void onGameQuestion(GameQuestionCommand command) {
        log.info("New question: " + command.getQuestionNumber() + ". " + command.getText());
        gui.setQuestionNumber(command.getQuestionNumber());
        gui.newQuestion(command.getQuestionNumber() + ". " + command.getText());
    }

    @Override
    protected void onPlayersLobbyUpdate(ShowPlayersListCommand command){
        log.info("Player in lobby: "+ command.getPlayers().toString());
        gui.listModel.removeAllElements();
        gui.listModel.addAll(command.getPlayers());
    }
}
