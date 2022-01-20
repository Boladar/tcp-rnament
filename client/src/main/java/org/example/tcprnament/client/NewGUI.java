package org.example.tcprnament.client;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Getter
@Setter
public class NewGUI {

    private final Socket clientSocket;
    private final TournamentProtocolApplication tournamentProtocolApplication;
    private Sender sender;

    private String joinGameName = "";
    private boolean gameOwner = false;
    private int questionNumber;

    private Thread updateGameList;
    private GameState currGameState;

    JFrame frame = new JFrame();
    JPanel panelCont = new JPanel();
    JPanel menuPanel = new JPanel();
    JPanel createGamePanel = new JPanel();
    JPanel loginPanel = new JPanel();
    JPanel gamePanel = new JPanel();
    JPanel waitingRoom = new JPanel();
    JPanel gameOver = new JPanel();
    JPanel setNickPanel = new JPanel();

    JButton createGameButton = new JButton("Create Game");
    JButton submitButton = new JButton("Submit");
    JButton joinGameButton = new JButton("Join");
    JButton yesButton = new JButton("Yes");
    JButton noButton = new JButton("No");
    JButton startGameButton = new JButton("Start Game");
    JButton goBackMenu = new JButton("Go back to Menu");
    JButton sendNickButton = new JButton("Set Nick");

    CardLayout cl = new CardLayout();

    JTextField gameName = new JTextField();
    JTextField gamePassword = new JTextField();
    JTextField loginPasswordField = new JTextField();
    JTextField nickSetField = new JTextField();

    JLabel nameLabel = new JLabel("Game name:");
    JLabel passwordLabel = new JLabel("Password:");
    JLabel loginPasswordLabel = new JLabel("Password:");
    JLabel scoreBoard = new JLabel();
    JLabel question = new JLabel();
    JLabel activePlayers = new JLabel();
    JLabel gameOverLabel = new JLabel();


    DefaultTableModel model = new DefaultTableModel();
    JTable activeGames = new JTable(model);

    List<String> activePlayersList = new ArrayList<>();

    public NewGUI(Socket clientSocket) {
        this.clientSocket = clientSocket;
        this.tournamentProtocolApplication = new TournamentProtocolApplication(clientSocket, this);
        this.sender = new Sender(clientSocket, tournamentProtocolApplication);

        updateGameList = new Thread(new UpdateGameList(sender, this));
        updateGameList.start();

        panelCont.setLayout(cl);

        //NickSetPanel
        nickSetField.setPreferredSize(new Dimension(250, 40));
        setNickPanel.add(nickSetField);
        setNickPanel.add(sendNickButton);

        //menuPanel
        menuPanel.add(createGameButton);
        model.addColumn("Name");

        menuPanel.add(activeGames);

        //createGamePanel
        gameName.setPreferredSize(new Dimension(250, 40));
        gamePassword.setPreferredSize(new Dimension(250, 40));

        createGamePanel.add(nameLabel);
        createGamePanel.add(gameName);

        createGamePanel.add(passwordLabel);
        createGamePanel.add(gamePassword);

        createGamePanel.add(submitButton);

        //LoginPanel
        loginPanel.add(loginPasswordLabel);
        loginPasswordField.setPreferredSize(new Dimension(250, 40));
        loginPanel.add(loginPasswordField);
        loginPanel.add(joinGameButton);

        //WaitingRoom
        waitingRoom.add(activePlayers);
        waitingRoom.add(startGameButton);

        //GamePanel
        gamePanel.add(question);
        gamePanel.add(yesButton);
        gamePanel.add(noButton);
        gamePanel.add(scoreBoard);

        //gameOver
        gameOver.add(gameOverLabel);
        gameOver.add(goBackMenu);

        //panelContainer
        panelCont.add(setNickPanel, GameState.SET_NICK.toString());
        panelCont.add(menuPanel, GameState.MENU.toString());
        panelCont.add(createGamePanel, GameState.CREATE_GAME.toString());
        panelCont.add(loginPanel, GameState.LOGIN_PANEL.toString());
        panelCont.add(gamePanel, GameState.GAME_PANEL.toString());
        panelCont.add(waitingRoom, GameState.WAITING_ROOM.toString());
        panelCont.add(gameOver, GameState.GAME_OVER.toString());

        currGameState = GameState.SET_NICK;
        cl.show(panelCont, currGameState.toString());

        sendNickButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendNick(nickSetField.getText());
                currGameState = GameState.MENU;
                cl.show(panelCont, currGameState.toString());
            }
        });

        joinGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendJoinGame(loginPasswordField.getText());
            }
        });

        createGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currGameState = GameState.CREATE_GAME;
                cl.show(panelCont, currGameState.toString());
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

                joinGameName = name;
                sendJoinGame(password);

                gameOwner = true;
            }
        });

        activeGames.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(activeGames.getSelectedRow() > -1 && activeGames.getSelectedRow() < activeGames.getRowCount()){
                    joinGameName = activeGames.getValueAt(activeGames.getSelectedRow(), 0).toString();
                    currGameState = GameState.LOGIN_PANEL;
                    cl.show(panelCont, currGameState.toString());
                }
            }
        });

        startGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendGameStart();
            }
        });

        yesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendAns(true);
            }
        });

        noButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendAns(false);
            }
        });

        goBackMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    sender.getGameList();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                currGameState = GameState.MENU;
                cl.show(panelCont, currGameState.toString());

            }
        });


        //frameConfig
        frame.add(panelCont);
        frame.setTitle("La Grande Tournamentooo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.setSize(500, 500);
        frame.pack();
        frame.setVisible(true);

        UpdateGameTable();
    }


    private void UpdateGameTable() {
        try {
            this.sender.getGameList();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendNick(String nick){
        try {
            this.sender.setNick(nick);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendCreateGame(String name, String pass) {
        try {
            this.sender.sendCreateGame(name, pass);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendJoinGame(String pass) {
        try {
            this.sender.sendJoinGame(joinGameName, pass);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendGameStart() {
        try {
            this.sender.sendGameStarted();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendAns(boolean ans) {
        try {
            this.sender.sendAnswer(questionNumber, ans);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void rejectedInfoMessage(String reason) {
        //wyswietlic (moze zawsze na gorze ekranu) odrzucono komende z jakiegos powodu?
    }

    public void gameListReceived(List<String> data) {
        log.info("revcived games: " + data);
        model.setRowCount(0);
        if(data.size() > 0){
            data.forEach(r -> model.addRow(new Object[]{r}));
        }

    }

    public void gameJoined() {
        // zmienic gui na to z gry + (info o dołączeniu do gry?)
        currGameState = GameState.WAITING_ROOM;
        cl.show(panelCont, currGameState.toString());

    }

    public void addPlayer(String playerName) {
        this.activePlayersList.add(playerName);
    }

    public void removePlayer(String playerName) {
        this.activePlayersList.remove(playerName);
    }

    public void gameFinished(String finalScore) {
        this.gameOverLabel.setText(finalScore);
        currGameState = GameState.GAME_OVER;
        cl.show(panelCont, currGameState.toString());

    }

    public void updateScoreBoard(String scoreboard) {
        this.scoreBoard.setText(scoreboard);
    }

    public void newQuestion(String question) {
        this.question.setText(question);
    }

    public void gameStarted() {
        currGameState = GameState.GAME_PANEL;
        cl.show(panelCont, currGameState.toString());
    }
}
