package org.example.tcprnament.shared.commands.server.concrete;

import lombok.Getter;
import org.example.tcprnament.shared.commands.server.ServerCommand;
import org.example.tcprnament.shared.commands.server.ServerCommandType;

@Getter
public class GameQuestionCommand extends ServerCommand {
    private int questionNumber;
    private String text;

    public GameQuestionCommand(int questionNumber, String text) {
        super(ServerCommandType.GAME_QUESTION);
        this.questionNumber = questionNumber;
        this.text = text;
    }
}
