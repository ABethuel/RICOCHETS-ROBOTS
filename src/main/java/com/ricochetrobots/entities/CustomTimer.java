package com.ricochetrobots.entities;

import com.ricochetrobots.systems.GameController;
import javafx.application.Platform;

import java.util.TimerTask;

public class CustomTimer extends TimerTask {

    private int timer;
    private GameController gameController;
    private Game game;

    public CustomTimer(int timer, GameController gameController, Game game) {
        this.timer = timer;
        this.gameController = gameController;
        this.game = game;
    }

    @Override
    public void run() {
        Platform.runLater(() -> {
            gameController.vBoxTimer.setVisible(true);
            System.out.println("chrono : " + timer);
            gameController.timerLabel.setText(timer + "");
            game.setTimerOn(false);
            if (timer == 0){
                gameController.gridPane.setDisable(false);
                gameController.gridPane.setOpacity(1);
                System.out.println("Coups attendus : " + game.getNumberOfShotsExpected(gameController.numberOfShotsPlayer1, gameController.numberOfShotsPlayer2, gameController));
                gameController.textFieldPlayer2.setDisable(true);
                gameController.textFieldPlayer1.setDisable(true);
                gameController.validateShotsButton.setDisable(true);
                gameController.vBoxTimer.setVisible(false);
                cancel();
            }
            timer = timer - 1;
        });


    }
}
