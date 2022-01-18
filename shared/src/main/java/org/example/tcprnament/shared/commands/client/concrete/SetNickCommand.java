package org.example.tcprnament.shared.commands.client.concrete;

import lombok.Getter;
import org.example.tcprnament.shared.commands.client.ClientCommand;
import org.example.tcprnament.shared.commands.client.ClientCommandType;

@Getter
public class SetNickCommand extends ClientCommand {
    private String nick;

    public SetNickCommand(Long id, String nick) {
        super(id, ClientCommandType.SET_NICK);
        this.nick = nick;
    }
}
