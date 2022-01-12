package org.example.tcprnament.shared.commands.client.concrete;

import lombok.Getter;
import lombok.Setter;
import org.example.tcprnament.shared.commands.client.ClientCommand;
import org.example.tcprnament.shared.commands.client.ClientCommandType;

@Getter
@Setter
public class JoinGameCommand extends ClientCommand {
    private String name;

    public JoinGameCommand(String name) {
        super(ClientCommandType.JOIN_GAME);
        this.name = name;
    }
}
