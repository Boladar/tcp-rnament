package org.example.tcprnament.shared.parser;

import org.apache.commons.lang3.SerializationUtils;
import org.example.tcprnament.shared.commands.ClientCommand;
import org.example.tcprnament.shared.commands.ClientCommandType;
import org.junit.jupiter.api.Test;

class ClientParserTest {

    @Test
    void name() {

        ClientParser parser = new ClientParser() {

        };

        ClientCommand command = new ClientCommand(ClientCommandType.CREATE_GAME);
        byte[] bytes = SerializationUtils.serialize(command);

        parser.parse(bytes);

    }
}
