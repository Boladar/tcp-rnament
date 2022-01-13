package org.example.tcprnament.client;

import lombok.AllArgsConstructor;
import org.example.tcprnament.shared.commands.server.ServerCommand;
import org.example.tcprnament.shared.commands.server.ServerCommandParser;
import org.example.tcprnament.shared.commands.server.concrete.*;

@AllArgsConstructor
public class TournamentProtocolApplication extends ServerCommandParser {

    private final GUI gui;

    @Override
    protected void onGameCreated(GameCreatedCommand command) {

    }

    @Override
    protected void onGamesList(GamesListCommand command) {

    }

    @Override
    protected void onGameJoined(GameJoinedCommand command) {

    }

    @Override
    protected void onGameStarted(ServerCommand command) {

    }

    @Override
    protected void onCommandRejected(CommandRejected command) {

    }

    @Override
    protected void onPlayerJoined(PlayerJoinedCommand command) {

    }

    @Override
    protected void onPlayerLeft(PlayerLeftCommand command) {

    }

    @Override
    protected void onGameScoreUpdate(GameScoreUpdateCommand command) {

    }

    @Override
    protected void onGameQuestion(GameQuestionCommand command) {

    }
}
