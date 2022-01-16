package org.example.tcprnament.shared.commands.client.concrete;

import lombok.Getter;
import lombok.Setter;
import org.example.tcprnament.shared.commands.client.ClientCommand;
import org.example.tcprnament.shared.commands.client.ClientCommandType;

@Getter
@Setter
public class JoinGameCommand extends ClientCommand {
    private String name;
    private String password;

    public JoinGameCommand(Long id,String name) {
        super(id,ClientCommandType.JOIN_GAME);
        this.name = name;
    }

    public JoinGameCommand(Long id, String name, String password) {
        super(id, ClientCommandType.JOIN_GAME);
        this.name = name;
        this.password = password;
    }
}
