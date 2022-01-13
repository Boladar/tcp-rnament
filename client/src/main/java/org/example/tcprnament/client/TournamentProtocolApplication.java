package org.example.tcprnament.client;

import org.example.tcprnament.shared.commands.server.ServerCommand;
import org.example.tcprnament.shared.commands.server.ServerCommandParser;
import org.example.tcprnament.shared.commands.server.concrete.*;

public class TournamentProtocolApplication extends ServerCommandParser {
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
}
