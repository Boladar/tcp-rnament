package org.example.tcprnament.shared.commands.client.concrete;

import lombok.Getter;
import lombok.Setter;
import org.example.tcprnament.shared.commands.client.ClientCommand;
import org.example.tcprnament.shared.commands.client.ClientCommandType;

@Getter
@Setter
public class JoinGameCommand extends ClientCommand {
    private String name;

    public JoinGameCommand(ClientCommandType type, String name) {
        super(type);
        this.name = name;
    }
}
