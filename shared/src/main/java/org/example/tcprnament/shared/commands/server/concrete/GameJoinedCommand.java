package org.example.tcprnament.shared.commands.server.concrete;

import lombok.Getter;
import lombok.Setter;
import org.example.tcprnament.shared.commands.server.ServerCommand;
import org.example.tcprnament.shared.commands.server.ServerCommandType;

@Getter
@Setter
public class GameJoinedCommand extends ServerCommand {
    private String name;

    public GameJoinedCommand(String name) {
        super(ServerCommandType.GAME_JOINED);
        this.name = name;
    }
}
