package org.example.tcprnament.client;

import lombok.AllArgsConstructor;
import org.springframework.integration.ip.tcp.serializer.ByteArrayCrLfSerializer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

@AllArgsConstructor
public class Receiver implements Runnable{
    private Socket clientSocket;
    private TournamentProtocolApplication tournamentProtocolApplication;

    @Override
    public void run() {
        while(true){
            ByteArrayCrLfSerializer byteArrayCrLfSerializer = new ByteArrayCrLfSerializer();
            byte[] message = new byte[Short.MAX_VALUE];

            try {
                byteArrayCrLfSerializer.doDeserialize(clientSocket.getInputStream(), message);
            } catch (IOException e) {
                e.printStackTrace();
            }

            tournamentProtocolApplication.parse(message, null);
        }
    }
}
