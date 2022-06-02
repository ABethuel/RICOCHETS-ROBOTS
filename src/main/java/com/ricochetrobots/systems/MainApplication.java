package com.ricochetrobots.systems;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;


import java.io.IOException;
import java.nio.file.Paths;

public class MainApplication extends Application {

    public static Stage stage;

    @Override
    public void start(Stage stage) throws IOException {

        music();

        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("menu-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 600);
        MainApplication.stage = stage;

        stage.setTitle("RICOCHETS ROBOTS");
        stage.getIcons().add(new Image("file:assets/icon.png"));
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();

    }

        MediaPlayer mediaPlayer;

        public void music() {

            String s = "assets/musicDuJeu.mp3";
            Media h = new Media(Paths.get(s).toUri().toString());
            mediaPlayer = new MediaPlayer(h);
            mediaPlayer.play();
    }

    public static void main(String[] args) {
        launch();
    }
}