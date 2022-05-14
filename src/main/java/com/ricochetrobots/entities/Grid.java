package com.ricochetrobots.entities;

import com.ricochetrobots.components.Orientation;
import com.ricochetrobots.systems.GameController;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Grid {
    private final Wall[][] grid = new Wall[16][16];

    public Wall[][] getGrid() {return grid;}


    public void setWall (int x, int y, Orientation orientation) {
        grid[x][y] = new Wall(x, y, orientation);
    }
}
