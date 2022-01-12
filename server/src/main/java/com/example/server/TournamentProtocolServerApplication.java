package com.example.server;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.tcprnament.shared.commands.client.ClientCommand;
import org.example.tcprnament.shared.commands.client.ClientCommandParser;
import org.example.tcprnament.shared.commands.client.concrete.CreateGameCommand;
import org.example.tcprnament.shared.commands.client.concrete.JoinGameCommand;
import org.example.tcprnament.shared.commands.server.concrete.GameCreatedCommand;
import org.example.tcprnament.shared.model.Game;
import org.springframework.context.annotation.DependsOn;
import org.springframework.integration.ip.tcp.connection.AbstractServerConnectionFactory;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
@DependsOn("gateway")
@RequiredArgsConstructor
public class TournamentProtocolServerApplication extends ClientCommandParser {

    private final AbstractServerConnectionFactory connectionFactory;
    private final Gateway gateway;

    Map<String, Game> currentGames = new ConcurrentHashMap<>();

    @Override
    protected void onCreateGame(CreateGameCommand command) {
        if (!currentGames.containsKey(command.getName())) {
            currentGames.put(command.getName(), new Game(command.getName()));
            log.info("Created game with name {}", command.getName());
        }

        gateway.send(serialize(new GameCreatedCommand(command.getName())), command.getConnectionId());
    }

    @Override
    protected void onShowGames(ClientCommand command) {

    }

    @Override
    protected void onJoinGame(JoinGameCommand command) {

    }

    @Override
    protected void onStartGame(ClientCommand command) {

    }
}
