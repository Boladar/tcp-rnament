package org.example.tcprnament.shared.commands.server.concrete;

import lombok.Getter;
import lombok.Setter;
import org.example.tcprnament.shared.commands.server.ServerCommand;
import org.example.tcprnament.shared.commands.server.ServerCommandType;
import org.example.tcprnament.shared.model.Game;

import java.util.List;

@Getter
@Setter
public class GamesListCommand extends ServerCommand {
    private List<Game> games;

    public GamesListCommand(List<Game> games) {
        super(ServerCommandType.GAMES_LIST);
        this.games = games;
    }
}
