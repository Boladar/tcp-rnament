package org.example.tcprnament.client;

import org.example.tcprnament.shared.commands.server.ServerCommand;
import org.example.tcprnament.shared.commands.server.ServerCommandParser;
import org.example.tcprnament.shared.commands.server.concrete.GameCreatedCommand;
import org.example.tcprnament.shared.commands.server.concrete.GameJoinedCommand;
import org.example.tcprnament.shared.commands.server.concrete.GamesListCommand;

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
}
