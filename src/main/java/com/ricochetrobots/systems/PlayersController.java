package com.ricochetrobots.systems;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class PlayersController {
    ObservableList<String> list= FXCollections.observableArrayList();

    public ChoiceBox<String> choiceNumberOfPlayers;
    public Button validateNumberButton;
    public VBox vBoxPlayer1;
    public TextField textFieldPlayer1;
    public VBox vBoxPlayer2;
    public TextField textFieldPlayer2;
    public Button startGameButton;

    private int numberOfPlayers;
    private String namePlayer1;
    private String namePlayer2;

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

    private void loadChoiceBox(){
        list.removeAll();
        list.addAll("1", "2");
        choiceNumberOfPlayers.getItems().addAll(list);
    }

    public void validateNumberOnClick(ActionEvent actionEvent) {
        setNumberOfPlayers(Integer.parseInt(choiceNumberOfPlayers.getValue()));
        if (getNumberOfPlayers() == 1){
            vBoxPlayer1.setVisible(true);
        }else{
            vBoxPlayer1.setVisible(true);
            vBoxPlayer2.setVisible(true);
        }
        startGameButton.setVisible(true);
    }

    public void startGameOnClick(ActionEvent actionEvent) {
    }
}
