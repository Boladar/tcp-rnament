package org.example.tcprnament.client;

import lombok.AllArgsConstructor;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@AllArgsConstructor
public class UpdateGameList implements Runnable{

    private final Sender sender;
    private final  NewGUI gui;

    @Override
    public void run() {
        while(true){
            if(gui.getCurrGameState() == GameState.MENU){
                try {
                    sender.getGameList();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
