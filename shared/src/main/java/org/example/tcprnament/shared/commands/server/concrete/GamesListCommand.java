package org.example.tcprnament.shared.commands.server.concrete;

import lombok.Getter;
import lombok.Setter;
import org.example.tcprnament.shared.commands.server.ServerCommand;
import org.example.tcprnament.shared.commands.server.ServerCommandType;

import java.util.List;

@Getter
@Setter
public class GamesListCommand extends ServerCommand {
    private List<String> games;

    public GamesListCommand(List<String> games) {
        super(ServerCommandType.GAMES_LIST);
        this.games = games;
    }
}
