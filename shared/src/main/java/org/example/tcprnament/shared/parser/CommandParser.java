package org.example.tcprnament.shared.parser;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.SerializationUtils;
import org.example.tcprnament.shared.commands.Command;
import org.example.tcprnament.shared.commands.client.ClientCommand;
import org.springframework.integration.ip.tcp.serializer.ByteArrayCrLfSerializer;

import java.io.IOException;
import java.io.OutputStream;

@Slf4j
public abstract class CommandParser {

    private final ByteArrayCrLfSerializer byteArrayCrLfSerializer = new ByteArrayCrLfSerializer();

    public void write(Command command, OutputStream outputStream) throws IOException {
        byte[] message = serialize(command);
        byteArrayCrLfSerializer.serialize(message, outputStream);
    }

    public byte[] serialize(Command command) {
        return SerializationUtils.serialize(command);
    }

    public void parse(byte[] objectData, String connectionId) {
        Command base = SerializationUtils.deserialize(objectData);
        log.info("Parsing new Command of side: {}", base.getSide());

        switch (base.getSide()) {
            case SERVER:
                parseServerCommand(base);
                break;
            case CLIENT:
                ClientCommand command = (ClientCommand) base;

                if (connectionId != null && !connectionId.isBlank()) {
                    command.setConnectionId(connectionId);
                }
                parseClientCommand(command);
                break;
        }

    }

    protected void parseServerCommand(Command command) {

    }

    protected void parseClientCommand(ClientCommand command) {

    }
}
