package com.ricochetrobots.entities;

import com.ricochetrobots.systems.GameController;
import javafx.application.Platform;

import java.util.TimerTask;

// Timer personnalisé lorsque l'on saisit le nombre de coups
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
        // Platform.runLater ---> Timer fonctionnant pour JFX
        Platform.runLater(() -> {
            gameController.vBoxTimer.setVisible(true); // On affiche le timer
            System.out.println("chrono : " + timer);
            gameController.timerLabel.setText(timer + ""); // On affiche la valeur
            game.setTimerOn(false);                        // Pour ne pas que le timer se lance à chaque clique
            if (timer == 0){                                // Lorsque l'on arrive à la fin du timer
                gameController.gridPane.setDisable(false);  // Activation du plateau
                gameController.gridPane.setOpacity(1);      // Opacité normale
                System.out.println("Coups attendus : " + game.getNumberOfShotsExpected(gameController.numberOfShotsPlayer1, gameController.numberOfShotsPlayer2, gameController));
                gameController.textFieldPlayer2.setDisable(true);
                gameController.textFieldPlayer1.setDisable(true);
                gameController.validateShotsButton.setDisable(true);
                gameController.vBoxTimer.setVisible(false);
                cancel();                                   // On arrete le timer
            }
            timer = timer - 1;                              // On diminuer chaque seconde le temps restants
        });
    }
}
