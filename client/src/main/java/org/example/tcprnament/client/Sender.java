package org.example.tcprnament.client;


import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.example.tcprnament.shared.commands.client.concrete.CreateGameCommand;
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
}
