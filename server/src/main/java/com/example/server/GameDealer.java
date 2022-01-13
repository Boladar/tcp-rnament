package com.example.server;

import com.example.server.model.Game;
import lombok.extern.slf4j.Slf4j;
import org.example.tcprnament.shared.commands.server.concrete.GameQuestionCommand;
import org.example.tcprnament.shared.commands.server.concrete.GameScoreUpdateCommand;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
public class GameDealer implements Runnable {

    private final TournamentProtocolServerApplication application;
    private final Gateway gateway;
    private final Game game;
    private final ScheduledExecutorService gameExecutor;

    public GameDealer(TournamentProtocolServerApplication application, Game game) {
        this.application = application;
        this.gateway = application.getGateway();
        this.gameExecutor = application.getGameExecutor();
        this.game = game;
    }

    @Override
    public void run() {
        log.info("[Update Game: {}]", game.getName());
        log.info("[Update Game: {}] --> sending GameScoreUpdateCommand", game.getName());

        boolean finished = game.getCurrentQuestion() >= game.getGameQuestions().size();

        GameScoreUpdateCommand scoreUpdateCommand = new GameScoreUpdateCommand(game.getPlayers(), finished);

        game.getPlayers().forEach((player, score) -> {
            gateway.send(application.serialize(scoreUpdateCommand), player);
        });

        if (!finished) {
            log.info("[Update Game: {}] --> dealing question number: {}", game.getName(), game.getCurrentQuestion());
            game.setCurrentQuestion(game.getCurrentQuestion() + 1);

            GameQuestionCommand gameQuestionCommand = new GameQuestionCommand(
                    game.getCurrentQuestion(),
                    game.getGameQuestions()
                        .get(game.getCurrentQuestion())
                        .getText()
            );

            game.getPlayers().forEach((player, score) -> {
                gateway.send(application.serialize(gameQuestionCommand), player);
            });

            gameExecutor.schedule(this, 5, TimeUnit.SECONDS);
        } else {
            log.info("Game: {}, reached final question ending the game!", game.getName());
        }


    }
}
