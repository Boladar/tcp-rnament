package org.example.tcprnament.shared.parser;

import org.apache.commons.lang3.SerializationUtils;
import org.example.tcprnament.shared.commands.client.*;
import org.example.tcprnament.shared.commands.client.concrete.CreateGameCommand;
import org.example.tcprnament.shared.commands.client.concrete.JoinGameCommand;
import org.junit.jupiter.api.Test;

class ClientCommandParserTest {

    @Test
    void name() {

        ClientCommandParser parser = new ClientCommandParser() {

            @Override
            protected void onCreateGame(CreateGameCommand command) {

            }

            @Override
            protected void onShowGames(ClientCommand command) {

            }

            @Override
            protected void onJoinGame(JoinGameCommand command) {

            }

            @Override
            protected void onStartGame(ClientCommand command) {

            }
        };

        ClientCommand command = new ClientCommand(ClientCommandType.CREATE_GAME);
        byte[] bytes = SerializationUtils.serialize(command);

        parser.parse(bytes);

    }
}
