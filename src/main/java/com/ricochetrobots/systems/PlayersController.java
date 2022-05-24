package com.ricochetrobots.systems;

import com.ricochetrobots.entities.Player;
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
        choiceNumberOfPlayers.setDisable(true);
    }

    public void startGameOnClick(ActionEvent actionEvent) throws IOException {
        newPlayer(new Player(textFieldPlayer1.getText()));
        if (textFieldPlayer2.getText() != null && !Objects.equals(textFieldPlayer2.getText(), "") && !Objects.equals(textFieldPlayer2.getText(), " ")){
            newPlayer(new Player(textFieldPlayer2.getText()));
        }
        System.out.println(players);
        if (textFieldPlayer1.getText() != null && !Objects.equals(textFieldPlayer1.getText(), "") && !Objects.equals(textFieldPlayer1.getText(), " ")) {
            MainApplication.stage.setUserData(players);
            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("game-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1000, 700);
            MainApplication.stage.setScene(scene);
            MainApplication.stage.show();
        }
    }

    private void newPlayer(Player player){
        players.add(player);
    }

    public void checkValue(KeyEvent keyEvent) {
    }
}
