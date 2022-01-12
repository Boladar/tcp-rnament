package org.example.tcprnament.shared.commands.client.concrete;

import lombok.Getter;
import lombok.Setter;
import org.example.tcprnament.shared.commands.client.ClientCommand;
import org.example.tcprnament.shared.commands.client.ClientCommandType;

@Getter
@Setter
public class CreateGameCommand extends ClientCommand {
    private String name;
    private String password;

    public CreateGameCommand(ClientCommandType type, String name, String password) {
        super(type);
        this.name = name;
        this.password = password;
    }
}
