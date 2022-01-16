package org.example.tcprnament.client;


import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.example.tcprnament.shared.commands.client.ClientCommand;
import org.example.tcprnament.shared.commands.client.ClientCommandType;
import org.example.tcprnament.shared.commands.client.concrete.CreateGameCommand;
import org.example.tcprnament.shared.commands.client.concrete.JoinGameCommand;
import org.example.tcprnament.shared.commands.client.concrete.QuestionAnswerCommand;
import org.springframework.integration.ip.tcp.serializer.ByteArrayCrLfSerializer;

import java.io.IOException;
import java.net.Socket;

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
    }
}
