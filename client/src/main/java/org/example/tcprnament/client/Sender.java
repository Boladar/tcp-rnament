package org.example.tcprnament.client;


import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.example.tcprnament.shared.commands.client.ClientCommand;
import org.example.tcprnament.shared.commands.client.ClientCommandType;
import org.example.tcprnament.shared.commands.client.concrete.*;
import org.example.tcprnament.shared.commands.server.concrete.PlayerLeftCommand;
import org.example.tcprnament.shared.commands.server.concrete.ShowPlayersListCommand;
import org.springframework.integration.ip.tcp.serializer.ByteArrayCrLfSerializer;

import java.io.IOException;
import java.net.Socket;
import java.util.List;

@Slf4j
@Getter
@Setter
public class Sender {
    private Long commandCounter = 0L;

    private final TournamentProtocolApplication tournamentProtocolApplication;

    private final Socket clientSocket;

    public Sender(Socket clientSocket, TournamentProtocolApplication tournamentProtocolApplication) {
        this.tournamentProtocolApplication = tournamentProtocolApplication;
        this.clientSocket = clientSocket;
    }

    public void sendCreateGame(String name, String password) throws IOException {
        log.info("CreateGameCommand: Name: " + name + ", password: " + password);
        tournamentProtocolApplication.write(new CreateGameCommand(commandCounter, name, password), clientSocket.getOutputStream());
        commandCounter++;
    }

    public void getGameList() throws IOException {
        log.info("Sending gameList request");
        tournamentProtocolApplication.write(new ClientCommand(commandCounter, ClientCommandType.SHOW_GAMES), clientSocket.getOutputStream());
        commandCounter++;
    }

    public void sendJoinGame(String joinGameName, String pass) throws IOException {
        log.info("Sending join game request");
        tournamentProtocolApplication.write(new JoinGameCommand(commandCounter,joinGameName, pass), clientSocket.getOutputStream());
        commandCounter++;
    }

    public void sendGameStarted() throws IOException {
        log.info("Sending start game request");
        tournamentProtocolApplication.write(new ClientCommand(commandCounter, ClientCommandType.START_GAME), clientSocket.getOutputStream() );
        commandCounter++;
    }

    public void sendAnswer(int questionNumber, boolean ans) throws IOException {
        log.info("Sending answer for question {}: {}", questionNumber,ans);
        tournamentProtocolApplication.write(new QuestionAnswerCommand(commandCounter, questionNumber, ans), clientSocket.getOutputStream());
        commandCounter++;
    }

    public void setNick(String nick) throws IOException {
        log.info("Sending player's nick to be set to: "+nick);
        tournamentProtocolApplication.write(new SetNickCommand(commandCounter,nick), clientSocket.getOutputStream());
        commandCounter++;
    }
    public void lobbyPlayersUpdate(List<String> players) throws IOException {
        log.info("Sending lobby players update request");
        tournamentProtocolApplication.write(new GetPlayersListCommand(commandCounter,players),clientSocket.getOutputStream());
        commandCounter++;
    }
    public void sendLeaveLobby(String name) throws IOException {
        log.info("Sending leaving lobby request");
        tournamentProtocolApplication.write(new PlayerLeftCommand(name),clientSocket.getOutputStream());
    }


}
