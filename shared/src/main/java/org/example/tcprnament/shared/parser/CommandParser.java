package org.example.tcprnament.shared.parser;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.SerializationUtils;
import org.example.tcprnament.shared.commands.Command;

@Slf4j
public abstract class CommandParser {

    public byte[] serialize(Command command) {
        return SerializationUtils.serialize(command);
    }

    public void parse(byte[] objectData) {
        Command base = SerializationUtils.deserialize(objectData);
        log.info("Parsing new Command of side: {}", base.getSide());

        switch (base.getSide()) {
            case SERVER:
                parseServerCommand(base);
                break;
            case CLIENT:
                parseClientCommand(base);
                break;
        }

    }

    protected void parseServerCommand(Command command) {

    }

    protected void parseClientCommand(Command command) {

    }
}
