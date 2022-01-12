package org.example.tcprnament.shared.parser;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.SerializationUtils;
import org.example.tcprnament.shared.commands.client.ClientCommand;
import org.example.tcprnament.shared.commands.client.ClientCommandParser;
import org.example.tcprnament.shared.commands.client.concrete.CreateGameCommand;
import org.example.tcprnament.shared.commands.client.concrete.JoinGameCommand;
import org.junit.jupiter.api.Test;

@Slf4j
class ClientCommandParserTest {

    @Test
    void name() {

        ClientCommandParser parser = new ClientCommandParser() {

            @Override
            protected void onCreateGame(CreateGameCommand command) {
                log.info("Game create");
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

        ClientCommand command = new CreateGameCommand("abc", "abc");
        byte[] bytes = SerializationUtils.serialize(command);

        parser.parse(bytes);

    }
}
