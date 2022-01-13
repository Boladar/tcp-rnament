package org.example.tcprnament.shared.commands.server.concrete;

import lombok.Getter;
import lombok.Setter;
import org.example.tcprnament.shared.commands.client.ClientCommandType;
import org.example.tcprnament.shared.commands.server.ServerCommand;
import org.example.tcprnament.shared.commands.server.ServerCommandType;

@Setter
@Getter
public class CommandRejected extends ServerCommand {

    private Long refCommandId;
    private ClientCommandType refCommandType;
    private String reason;

    public CommandRejected(
            Long refCommandId,
            ClientCommandType refCommandType,
            String reason
    ) {
        super(ServerCommandType.COMMAND_REJECTED);
        this.refCommandId = refCommandId;
        this.refCommandType = refCommandType;
        this.reason = reason;
    }
}
