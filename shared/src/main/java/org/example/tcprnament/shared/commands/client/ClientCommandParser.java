package org.example.tcprnament.shared.commands.client;

import lombok.extern.slf4j.Slf4j;
import org.example.tcprnament.shared.commands.client.concrete.*;
import org.example.tcprnament.shared.commands.server.concrete.ShowPlayersListCommand;
import org.example.tcprnament.shared.parser.CommandParser;

@Slf4j
public abstract class ClientCommandParser extends CommandParser {

    @Override
    protected void parseClientCommand(ClientCommand clientCommand) {
        log.info("Parsing {} command", clientCommand.getType());
        switch (clientCommand.getType()) {
            case CREATE_GAME:
                onCreateGame((CreateGameCommand) clientCommand);
                break;
            case SHOW_GAMES:
                onShowGames(clientCommand);
                break;
            case JOIN_GAME:
                onJoinGame((JoinGameCommand) clientCommand);
                break;
            case LEAVE_GAME:
                onLeaveGame(clientCommand);
                break;
            case START_GAME:
                onStartGame(clientCommand);
                break;
            case QUESTION_ANSWER:
                onQuestionAnswer((QuestionAnswerCommand) clientCommand);
                break;
            case SET_NICK:
                onSetNick((SetNickCommand) clientCommand);
                break;
            case GET_PLAYERS:
                onPlayersListUpdate((GetPlayersListCommand)clientCommand);
                break;
        }
    }



    protected abstract void onSetNick(SetNickCommand command) ;

    protected abstract void onCreateGame(CreateGameCommand command);

    protected abstract void onShowGames(ClientCommand command);

    protected abstract void onJoinGame(JoinGameCommand command);

    protected abstract void onStartGame(ClientCommand command);

    protected abstract void onLeaveGame(ClientCommand command);

    protected abstract void onPlayersListUpdate(GetPlayersListCommand command);

    protected abstract void onQuestionAnswer(QuestionAnswerCommand command);
}
