package org.example.tcprnament.shared.commands;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientCommand extends Command {
    protected ClientCommandType type;

    public ClientCommand(ClientCommandType type) {
        super(CommandSide.CLIENT);
        this.type = type;
    }
}
