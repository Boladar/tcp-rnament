package org.example.tcprnament.shared.parser;

import lombok.extern.slf4j.Slf4j;
import org.example.tcprnament.shared.commands.ClientCommand;
import org.example.tcprnament.shared.commands.Command;

@Slf4j
public abstract class ClientParser extends CommandParser {

    @Override
    protected void parseServerCommand(Command command) {
        //ignored
    }

    @Override
    protected void parseClientCommand(Command command) {
        ClientCommand clientCommand = (ClientCommand) command;
        switch (clientCommand.getType()) {

            case CREATE_GAME:
                log.info("Parsing CREATE_GAME command");
                break;
            case SHOW_GAMES:
                log.info("Parsing SHOW_GAMES command");
                break;
            case JOIN_GAME:
                log.info("Parsing JOIN_GAME command");
                break;
            case START_GAME:
                log.info("Parsing START_GAME command");
                break;
        }
    }
}
