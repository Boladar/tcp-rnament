package org.example.tcprnament.shared.commands.client;

import lombok.extern.slf4j.Slf4j;
import org.example.tcprnament.shared.commands.Command;
import org.example.tcprnament.shared.commands.client.concrete.CreateGameCommand;
import org.example.tcprnament.shared.commands.client.concrete.JoinGameCommand;
import org.example.tcprnament.shared.parser.CommandParser;

@Slf4j
public abstract class ClientCommandParser extends CommandParser {

    @Override
    protected void parseClientCommand(Command command) {
        ClientCommand clientCommand = (ClientCommand) command;
        log.info("Parsing {} command", clientCommand.getType());
        switch (clientCommand.getType()) {
            case CREATE_GAME:
                onCreateGame((CreateGameCommand) clientCommand);
                break;
            case SHOW_GAMES:
                onShowGames(clientCommand);
                break;
            case JOIN_GAME:
                onJoinGame((JoinGameCommand) clientCommand);
                break;
            case START_GAME:
                onStartGame(clientCommand);
                break;
        }
    }

    protected abstract void onCreateGame(CreateGameCommand command);

    protected abstract void onShowGames(ClientCommand command);

    protected abstract void onJoinGame(JoinGameCommand command);

    protected abstract void onStartGame(ClientCommand command);
}
