package org.example.tcprnament.shared.commands.server.concrete;

import lombok.Getter;
import org.example.tcprnament.shared.commands.server.ServerCommand;
import org.example.tcprnament.shared.commands.server.ServerCommandType;

@Getter
public class PlayerLeftCommand extends ServerCommand {
    private String playerName;

    public PlayerLeftCommand(String playerName) {
        super(ServerCommandType.PLAYER_LEFT);
        this.playerName = playerName;
    }
}
