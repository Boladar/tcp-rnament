package org.example.tcprnament.client;

import lombok.extern.slf4j.Slf4j;

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


    void SendToServer(String message) throws Exception {
        PrintWriter out = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
        out.print(message + "\r\n");
        out.flush();
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
        client.SendToServer("1");
        System.out.println(client.ReceiveFromServer());
        clientSocket.close();
    }

}