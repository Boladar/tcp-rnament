package org.example.tcprnament.shared.commands.server.concrete;

import lombok.Getter;
import lombok.Setter;
import org.example.tcprnament.shared.commands.server.ServerCommand;
import org.example.tcprnament.shared.commands.server.ServerCommandType;

@Getter
@Setter
public class GameCreatedCommand extends ServerCommand {
    private String name;

    public GameCreatedCommand(String name) {
        super(ServerCommandType.GAME_CREATED);
        this.name = name;
    }
}
