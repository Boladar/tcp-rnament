package com.example.server.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Getter
@Setter
@AllArgsConstructor
public class Game {
    private GameState currentState;
    private String name;
    private String password;
    private Map<String, Integer> players = new ConcurrentHashMap<>();
    private String owner;

    private Map<String, Integer> wrongAnsCounter = new ConcurrentHashMap<>();
    private Map<String, Integer> lastAns = new ConcurrentHashMap<>();

    private int currentQuestion = 0;
    private List<Question> gameQuestions;

    public Game(String owner, String name, String password) {
        this.currentState = GameState.LOBBY;
        this.owner = owner;
        this.name = name;
        this.password = password;
    }

    public void addPlayer(String connectionId) {
        this.players.put(connectionId, 0);
    }

    public void removePlayer(String connectionId) {
        this.players.remove(connectionId);
    }

    public void setWrongAnsCounter(String player, int n){
        if(this.wrongAnsCounter.containsKey(player)){
            this.wrongAnsCounter.replace(player, n);
        }else{
            this.wrongAnsCounter.put(player, n);
        }
    }

    public void setLastAns(String player, int n){
        if(this.lastAns.containsKey(player)){
            this.lastAns.replace(player, n);
        }else{
            this.lastAns.put(player, n);
        }
    }
}
