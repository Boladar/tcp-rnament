package org.example.tcprnament.client;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;

@Slf4j
@Getter
@Setter
public class NewGUI {

    private final Socket clientSocket;
    private final TournamentProtocolApplication tournamentProtocolApplication;
    private Sender sender;



    JFrame frame = new JFrame();
    JPanel panelCont = new JPanel();
    JPanel menuPanel = new JPanel();
    JPanel createGamePanel = new JPanel();
    JButton createGameButton = new JButton("Create Game");
    JButton submitButton = new JButton("Submit");
    CardLayout cl = new CardLayout();
    JTextField gameName = new JTextField();
    JTextField gamePassword = new JTextField();
    JLabel nameLabel = new JLabel("Game name:");
    JLabel passwordLabel = new JLabel("Password:");


    public NewGUI(Socket clientSocket) {
        this.clientSocket = clientSocket;
        this.tournamentProtocolApplication = new TournamentProtocolApplication(clientSocket, this);
        this.sender = new Sender(clientSocket, tournamentProtocolApplication);

        panelCont.setLayout(cl);

        //menuPanel
        menuPanel.add(createGameButton);
        menuPanel.setBackground(Color.BLUE);

        //createGamePanel
        gameName.setPreferredSize(new Dimension(250,40));
        gamePassword.setPreferredSize(new Dimension(250,40));

        createGamePanel.add(nameLabel);
        createGamePanel.add(gameName);

        createGamePanel.add(passwordLabel);
        createGamePanel.add(gamePassword);

        createGamePanel.add(submitButton);

        //panelContainer
        panelCont.add(menuPanel, "menu");
        panelCont.add(createGamePanel, "createGame");
        cl.show(panelCont, "menu");




        createGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cl.show(panelCont,"createGame");
            }
        });

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ///tutaj logika przesyłania tekstów z JtextFieldów
                String name = gameName.getText();
                String password = gamePassword.getText();
                gameName.setText("");
                gamePassword.setText("");

                sendCreateGame(name, password);

                cl.show(panelCont,"menu");
            }
        });


        //frameConfig
        frame.add(panelCont);
        frame.setTitle("La Grande Tournamentooo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.setSize(500, 500);
        frame.pack();
        frame.setVisible(true);
    }

//    public static void main(String[] args, Socket clientSocket, TournamentProtocolApplication tournamentProtocolApplication) {
//        new NewGUI(clientSocket, tournamentProtocolApplication);
//
//    }

    private void sendCreateGame(String name, String pass){
        try {
            this.sender.sendCreateGame(name, pass);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
