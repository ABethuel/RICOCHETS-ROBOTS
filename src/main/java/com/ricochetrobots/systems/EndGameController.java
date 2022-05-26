package com.ricochetrobots.systems;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;

import java.io.IOException;

public class EndGameController {

    public Label player1;
    public Label player2;
    public Label winner;

    @FXML
    public void initialize(){

    }

    public void newGameOnClick(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("end-game-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 700);
        MainApplication.stage.setScene(scene);
        MainApplication.stage.show();
    }
}
