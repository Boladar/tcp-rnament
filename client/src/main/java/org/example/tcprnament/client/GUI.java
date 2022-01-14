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

    private int commandIdCount;
//////////////////////////
    CardLayout cardLayout = new CardLayout();
    JFrame frame = new JFrame();
    JButton createGameButton = new JButton("Create Game");
    JPanel joinGamePanel = new JPanel();
    JPanel createGamePanel = new JPanel();
    JPanel quiz = new JPanel();
    JPanel panel2 = new JPanel();
//////////////////////////////// joinGame
    JTable jTable = new JTable();
    /////////////////// createGame
    JLabel passwordLabel = new JLabel("Hasło:");
    JTextField passwordText = new JTextField(10);
    JButton submitPasswordButton = new JButton();
    /////////////// quiz
    JLabel roundLabel = new JLabel();
    JLabel scoreLabel = new JLabel();
    JLabel questionLabel = new JLabel();
    JButton yesAns = new JButton();
    JButton noAns = new JButton();

    public GUI() {
        //global
        createGameButton.setBounds(1,1,50,50);

        //joinGamePanel.setBorder(BorderFactory.createEmptyBorder(300, 300, 300, 300));
        joinGamePanel.setLayout(cardLayout);
        joinGamePanel.add(createGameButton);

        joinGamePanel.add(panel2, "2");
        panel2.setBackground(Color.BLUE);

        cardLayout.show(joinGamePanel, "joinGame");

        //layout joinGame
        joinGamePanel.add(jTable, "joinGame");

        //layout createGame
        createGamePanel.add(passwordLabel, "createGame");
        createGamePanel.add(passwordText, "createGame");
        createGamePanel.add(submitPasswordButton, "createGame");

        //layout quiz
        quiz.add(roundLabel, "quiz");
        quiz.add(scoreLabel, "quiz");
        quiz.add(questionLabel, "quiz");
        quiz.add(yesAns, "quiz");
        quiz.add(noAns, "quiz");


        createGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(joinGamePanel, "2");

            }

        });

        frame.add(joinGamePanel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Prototype GUI");
        frame.setSize(500,500);
        //    frame.pack();
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
