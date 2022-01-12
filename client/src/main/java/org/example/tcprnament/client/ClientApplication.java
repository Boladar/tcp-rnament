package org.example.tcprnament.client;

import lombok.extern.slf4j.Slf4j;
import org.example.tcprnament.shared.commands.client.concrete.CreateGameCommand;

import java.io.*;
import java.net.Socket;

@Slf4j
public class ClientApplication {
    public static Socket clientSocket;

    {
        try {
            clientSocket = new Socket("localhost", 2137);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    void SendToServer(byte[] message) throws Exception {
        String newline = "/r/n";
        byte[] n =newline.getBytes();
        //BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(clientSocket.getOutputStream()
        clientSocket.getOutputStream().write(message);
    }

    String ReceiveFromServer() throws Exception {
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        return in.readLine();
    }

    public static void main(String[] args) throws Exception {

        TournamentProtocolApplication tournamentProtocolApplication = new TournamentProtocolApplication();
        log.info("--------------------Starting client application-------------------------");
        log.info("test");
        log.error("error");

        ClientApplication client = new ClientApplication();
        client.SendToServer(tournamentProtocolApplication.serialize(new CreateGameCommand("dupa","dupa1")));

        byte[] message = client.ReceiveFromServer().getBytes();
        tournamentProtocolApplication.parse(message, null);
        clientSocket.close();
    }

}