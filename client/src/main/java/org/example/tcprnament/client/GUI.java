package org.example.tcprnament.client;

import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Socket;
import java.util.Vector;

@Setter
@Getter
public class GUI {
    private Socket clientSocket;
    private TournamentProtocolApplication tournamentProtocolApplication;

    private int commandIdCount;
//////////////////////////
    CardLayout cardLayout = new CardLayout();
    JFrame frame = new JFrame();
    JButton createGameButton = new JButton("Create Game");
    JPanel joinGamePanel = new JPanel();
    JPanel panel2 = new JPanel();
////////////////////////////////
    JTable jTable = new JTable();

    public GUI() {
        //global


        //joinGamePanel.setBorder(BorderFactory.createEmptyBorder(300, 300, 300, 300));
        joinGamePanel.setLayout(cardLayout);
        joinGamePanel.add(createGameButton);
        joinGamePanel.add(jTable);

        joinGamePanel.add(panel2, "2");
        panel2.setBackground(Color.BLUE);

        cardLayout.show(joinGamePanel, "joinGame");







        //layout joinGame


        //layout createGame


        //layout game


        createGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(joinGamePanel, "2");

            }

        });

        frame.add(joinGamePanel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Prototype GUI");
        frame.pack();
        frame.setVisible(true);


    }

    public static void main(String[] args) {
        new GUI();
    }

    public void updateGameList(Vector data, Vector  column) {
        jTable = new JTable(data, column);
        cardLayout.show(joinGamePanel, "joinGame");
    }
}
