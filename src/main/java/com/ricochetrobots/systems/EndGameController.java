package com.ricochetrobots.systems;

import com.ricochetrobots.entities.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;

import java.io.IOException;
import java.util.List;

public class EndGameController {
    private List<Player> players = (List<Player>) MainApplication.stage.getUserData();

    public Label player1;
    public Label player2;
    public Label winner;
    public Label easyGame;
    public Label muscleText;

    @FXML
    public void initialize() {
        System.out.println("--- Fin de partie ---");
        displayOneOrTwoPlayers();
        displayScores();
        displayWinner();
        displayBottomOnePlayer();
    }

    // On relance une partie
    public void newGameOnClick(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("menu-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 600);
        MainApplication.stage.setScene(scene);
        MainApplication.stage.show();
    }

    // On affiche 1 ou 2 joueurs selon les cas
    private void displayOneOrTwoPlayers(){
        if (players.size() == 1) player2.setVisible(false);
    }

    // On affiche les scores de 1 ou 2 joueurs selon les cas
    private void displayScores(){
        player1.setText(players.get(0).getName() + " : " + players.get(0).getScore());
        if (players.size() == 2)
            player2.setText(players.get(1).getName() + " : " + players.get(1).getScore());
    }

    // On affiche des textes différents selon les situations
    private void displayWinner(){
        if (players.size() == 1) winner.setText(players.get(0).getName() + " est le grand vainqueur !");
        else{
            if (players.get(0).getScore() > players.get(1).getScore()) {
                winner.setText(players.get(0).getName() + " est le grand vainqueur !");
                muscleText.setText("Muscle ton jeu " + players.get(1).getName() + " !");
            }else if (players.get(0).getScore() == players.get(1).getScore()){
                winner.setText("Egalité parfaite !");
                muscleText.setText("Faut régler ça à la bagarre maintenant !");
            }else {
                winner.setText(players.get(1).getName() + " est le grand vainqueur !");
                muscleText.setText("Muscle ton jeu " + players.get(0).getName() + " !");
            }
        }
    }

    // Affichage d'un texte en bas selon les situations
    private void displayBottomOnePlayer(){
        if (players.size() >= 2){
            easyGame.setVisible(false);
        }else{
            muscleText.setText("Muscle ton jeu " + players.get(0).getName() + " !");
        }
    }
}
