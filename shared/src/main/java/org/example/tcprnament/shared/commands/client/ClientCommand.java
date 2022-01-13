package org.example.tcprnament.shared.commands.client;

import lombok.Getter;
import lombok.Setter;
import org.example.tcprnament.shared.commands.Command;
import org.example.tcprnament.shared.commands.CommandSide;

@Getter
@Setter
public class ClientCommand extends Command {
    protected Long id;
    protected ClientCommandType type;
    protected String connectionId;

    public ClientCommand(Long id, ClientCommandType type) {
        super(CommandSide.CLIENT);
        this.type = type;
        this.id = id;
    }
}
