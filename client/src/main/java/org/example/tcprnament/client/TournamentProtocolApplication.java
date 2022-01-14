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
        System.out.println("!GÓWNO OŻYŁO!");
    }

    @Override
    protected void onGamesList(GamesListCommand command) {

    }

    @Override
    protected void onGameJoined(GameJoinedCommand command) {

    }

    @Override
    protected void onGameStarted(ServerCommand command) {

    }

    @Override
    protected void onCommandRejected(CommandRejected command) {
        log.info("Brawo cos zjebales! " + command.getReason());
    }

    @Override
    protected void onPlayerJoined(PlayerJoinedCommand command) {

    }

    @Override
    protected void onPlayerLeft(PlayerLeftCommand command) {

    }

    @Override
    protected void onGameScoreUpdate(GameScoreUpdateCommand command) {

    }

    @Override
    protected void onGameQuestion(GameQuestionCommand command) {

    }
}
