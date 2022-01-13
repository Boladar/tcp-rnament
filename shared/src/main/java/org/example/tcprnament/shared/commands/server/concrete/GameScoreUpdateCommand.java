package org.example.tcprnament.shared.commands.server.concrete;

import lombok.Getter;
import org.example.tcprnament.shared.commands.server.ServerCommand;
import org.example.tcprnament.shared.commands.server.ServerCommandType;

import java.util.Map;

@Getter
public class GameScoreUpdateCommand extends ServerCommand {
    private Map<String, Integer> scoreboard;
    private boolean finished;

    public GameScoreUpdateCommand(Map<String, Integer> scoreboard,boolean finished) {
        super(ServerCommandType.GAME_SCORE_UPDATE);
        this.scoreboard = scoreboard;
        this.finished = finished;
    }
}
