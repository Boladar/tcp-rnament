package org.example.tcprnament.shared.commands.server;

import lombok.extern.slf4j.Slf4j;
import org.example.tcprnament.shared.commands.Command;
import org.example.tcprnament.shared.commands.server.concrete.*;
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
            case COMMAND_REJECTED:
                onCommandRejected((CommandRejected) command);
                break;
            case PLAYER_JOINED:
                onPlayerJoined((PlayerJoinedCommand) command);
                break;
            case PLAYER_LEFT:
                onPlayerLeft((PlayerLeftCommand) command);
                break;
            case GAME_SCORE_UPDATE:
                onGameScoreUpdate((GameScoreUpdateCommand) command);
                break;
            case GAME_QUESTION:
                onGameQuestion((GameQuestionCommand) command);
                break;
        }
    }

    protected abstract void onGameCreated(GameCreatedCommand command);

    protected abstract void onGamesList(GamesListCommand command);

    protected abstract void onGameJoined(GameJoinedCommand command);

    protected abstract void onGameStarted(ServerCommand command);

    protected abstract void onCommandRejected(CommandRejected command);

    protected abstract void onPlayerJoined(PlayerJoinedCommand command);

    protected abstract void onPlayerLeft(PlayerLeftCommand command);

    protected abstract void onGameScoreUpdate(GameScoreUpdateCommand command);

    protected abstract void onGameQuestion(GameQuestionCommand command);

}
