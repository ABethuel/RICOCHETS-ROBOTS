package com.ricochetrobots.systems;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import java.io.IOException;

public class MenuViewController {
    public Button startButton;

    public void onStartClick(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("game-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 700);
        MainApplication.stage.setScene(scene);
        MainApplication.stage.show();
    }
}
