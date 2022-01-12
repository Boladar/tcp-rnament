package org.example.tcprnament.shared.commands.server;

import lombok.extern.slf4j.Slf4j;
import org.example.tcprnament.shared.commands.Command;
import org.example.tcprnament.shared.commands.server.concrete.GameCreatedCommand;
import org.example.tcprnament.shared.commands.server.concrete.GameJoinedCommand;
import org.example.tcprnament.shared.commands.server.concrete.GamesListCommand;
import org.example.tcprnament.shared.parser.CommandParser;

@Slf4j
public abstract class ServerCommandParser extends CommandParser {

    @Override
    protected void parseServerCommand(Command command) {
        ServerCommand serverCommand = (ServerCommand) command;
        log.info("Parsing {} command", serverCommand.getType());
        switch (serverCommand.type) {
            case GAME_CREATED:
                onGameCreated((GameCreatedCommand) serverCommand);
                break;
            case GAMES_LIST:
                onGamesList((GamesListCommand) serverCommand);
                break;
            case GAME_JOINED:
                onGameJoined((GameJoinedCommand) serverCommand);
                break;
            case GAME_STARTED:
                onGameStarted(serverCommand);
                break;
        }
    }

    protected abstract void onGameCreated(GameCreatedCommand command);

    protected abstract void onGamesList(GamesListCommand command);

    protected abstract void onGameJoined(GameJoinedCommand command);

    protected abstract void onGameStarted(ServerCommand command);

}
