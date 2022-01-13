package org.example.tcprnament.shared.commands.client.concrete;

import lombok.Getter;
import org.example.tcprnament.shared.commands.client.ClientCommand;
import org.example.tcprnament.shared.commands.client.ClientCommandType;

@Getter
public class QuestionAnswerCommand extends ClientCommand {
    private int questionNumber;
    private Boolean answer;

    public QuestionAnswerCommand(
            Long id,
            int questionNumber,
            Boolean answer
    ) {
        super(id, ClientCommandType.QUESTION_ANSWER);
        this.questionNumber = questionNumber;
        this.answer = answer;
    }
}
