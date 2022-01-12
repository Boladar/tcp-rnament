package org.example.tcprnament.shared.commands.server;

import lombok.Getter;
import lombok.Setter;
import org.example.tcprnament.shared.commands.Command;
import org.example.tcprnament.shared.commands.CommandSide;

@Getter
@Setter
public class ServerCommand extends Command {
    protected ServerCommandType type;

    public ServerCommand(ServerCommandType type) {
        super(CommandSide.SERVER);
        this.type = type;
    }
}
