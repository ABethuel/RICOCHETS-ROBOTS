package com.ricochetrobots.systems;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import java.io.IOException;

public class MenuViewController {
    public Button startButton;

    public void onStartClick(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("players-view.fxml"));
        //Scene scene = new Scene(fxmlLoader.load(), 1000, 700);
        Scene scene = new Scene(fxmlLoader.load(), 722, 472);
        MainApplication.stage.setScene(scene);
        MainApplication.stage.show();
    }

    public void onGuideClick(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("players-view.fxml"));
        //Scene scene = new Scene(fxmlLoader.load(), 1000, 700);
        Scene scene = new Scene(fxmlLoader.load(), 722, 472);
        MainApplication.stage.setScene(scene);
        MainApplication.stage.show();
    }

    public void goGuideClick(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("guide-game.fxml"));
        //Scene scene = new Scene(fxmlLoader.load(), 1000, 700);
        Scene scene = new Scene(fxmlLoader.load(), 1000, 600);
        MainApplication.stage.setScene(scene);
        MainApplication.stage.show();
    }

}
