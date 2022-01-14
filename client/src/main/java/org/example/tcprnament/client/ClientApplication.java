package org.example.tcprnament.client;

import lombok.extern.slf4j.Slf4j;
import org.example.tcprnament.shared.commands.client.concrete.CreateGameCommand;
import org.springframework.integration.ip.tcp.serializer.ByteArrayCrLfSerializer;

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
        byte[] newline = "/r/n".getBytes();
        OutputStream outputStream = clientSocket.getOutputStream();
        outputStream.write(message);
        outputStream.write(newline);
        outputStream.flush();
    }

    String ReceiveFromServer() throws Exception {
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        return in.readLine();
    }

    public static void main(String[] args) throws Exception {
        log.info("--------------------Starting client application-------------------------");
        log.info("test");
        log.error("error");

        ClientApplication client = new ClientApplication();


        NewGUI gui = new NewGUI(clientSocket);
        TournamentProtocolApplication tournamentProtocolApplication = new TournamentProtocolApplication(clientSocket, gui);
        Thread receiverThread = new Thread(new Receiver(clientSocket, tournamentProtocolApplication));
        receiverThread.start();


//        tournamentProtocolApplication.write(new CreateGameCommand(1L,"dupa", "dupa1"),clientSocket.getOutputStream());
//        ByteArrayCrLfSerializer byteArrayCrLfSerializer = new ByteArrayCrLfSerializer();
//
//        byte[] message = new byte[Short.MAX_VALUE];
//        byteArrayCrLfSerializer.doDeserialize(clientSocket.getInputStream(),message);
//        tournamentProtocolApplication.parse(message, null);
//        clientSocket.close();
    }

}
