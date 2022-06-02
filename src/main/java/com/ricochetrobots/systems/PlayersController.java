package com.ricochetrobots.systems;

import com.ricochetrobots.entities.Player;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PlayersController {

    ObservableList<String> list= FXCollections.observableArrayList();
    public List<Player> players = new ArrayList<Player>();

    public ChoiceBox<String> choiceNumberOfPlayers;
    public Button validateNumberButton;
    public VBox vBoxPlayer1;
    public TextField textFieldPlayer1;
    public VBox vBoxPlayer2;
    public TextField textFieldPlayer2;
    public Button startGameButton;
    public TextField textFieldScore;
    public VBox vBoxPlayer3;
    public Label textWarningScore;

    private int numberOfPlayers;
    private String namePlayer1;
    private String namePlayer2;
    private int scoreToReach;

    @FXML
    public void initialize(){
        loadChoiceBox();
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public String getNamePlayer1() {
        return namePlayer1;
    }

    public String getNamePlayer2() {
        return namePlayer2;
    }

    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    public void setNamePlayer1(String namePlayer1) {
        this.namePlayer1 = namePlayer1;
    }

    public void setNamePlayer2(String namePlayer2) {
        this.namePlayer2 = namePlayer2;
    }

    // Choix du nombre de joueur
    private void loadChoiceBox(){
        list.removeAll();
        list.addAll("1", "2");
        choiceNumberOfPlayers.getItems().addAll(list);
    }

    // On valide le nombre de joueur (1 ou 2)
    public void validateNumberOnClick(ActionEvent actionEvent) {
        setNumberOfPlayers(Integer.parseInt(choiceNumberOfPlayers.getValue()));
        if (getNumberOfPlayers() == 1){
            vBoxPlayer1.setVisible(true);
        }else{
            vBoxPlayer1.setVisible(true);
            vBoxPlayer2.setVisible(true);
        }
        vBoxPlayer3.setVisible(true);
        startGameButton.setVisible(true);
        choiceNumberOfPlayers.setDisable(true);
    }

    public void startGameOnClick(ActionEvent actionEvent) throws IOException {
        if (scoreToReach <= 16 && scoreToReach >= 1){
            newPlayer(new Player(textFieldPlayer1.getText()));
            if (textFieldPlayer2.getText() != null && !Objects.equals(textFieldPlayer2.getText(), "") && !Objects.equals(textFieldPlayer2.getText(), " ")){
                newPlayer(new Player(textFieldPlayer2.getText()));
            }
            System.out.println(players);
            if (textFieldPlayer1.getText() != null && !Objects.equals(textFieldPlayer1.getText(), "") && !Objects.equals(textFieldPlayer1.getText(), " ")) {
                List<Object> dataToTransmit = new ArrayList <Object>();
                dataToTransmit.add(players);
                dataToTransmit.add(scoreToReach);
                MainApplication.stage.setUserData(dataToTransmit);
                FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("game-view.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 1000, 700);
                MainApplication.stage.setScene(scene);
                MainApplication.stage.show();
            }
        }else { // Si l'on n'a pas rentré score atteignable un texte d'alerte s'affiche
            textWarningScore.setVisible(true);
        }
    }

    // On ajoute un joueur
    private void newPlayer(Player player){
        players.add(player);
    }

    @FXML
    public void checkValue(KeyEvent keyEvent) {
        textFieldScore.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    textFieldScore.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        scoreToReach = Integer.parseInt(textFieldScore.getText());
    }
}
