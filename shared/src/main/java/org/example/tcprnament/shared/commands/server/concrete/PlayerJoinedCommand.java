package org.example.tcprnament.shared.commands.server.concrete;

import lombok.Getter;
import org.example.tcprnament.shared.commands.server.ServerCommand;
import org.example.tcprnament.shared.commands.server.ServerCommandType;

@Getter
public class PlayerJoinedCommand extends ServerCommand {
    private String playerName;

    public PlayerJoinedCommand(String playerName) {
        super(ServerCommandType.PLAYER_JOINED);
        this.playerName = playerName;
    }
}
