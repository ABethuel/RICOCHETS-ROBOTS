package com.ricochetrobots.entities;

import com.ricochetrobots.systems.GameController;
import com.ricochetrobots.systems.MainApplication;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {

    @Test
    public void setNumberOfShotsTest(){
        List<Player> players = new ArrayList<>();
        players.add(new Player("Player1"));
        players.add(new Player("Player2"));

        Game game = new Game(players, 12 );
        GameController gameController = new GameController();
        gameController.textFieldPlayer2.setText("12");
        gameController.textFieldPlayer2.setText("2");

        int numberOfShotsPlayer1 = Integer.parseInt(gameController.textFieldPlayer1.getText());
        int numberOfShotsPlayer2 = Integer.parseInt(gameController.textFieldPlayer2.getText());

        assertEquals(game.getNumberOfShotsExpected(numberOfShotsPlayer1, numberOfShotsPlayer2, gameController), 2);
    }

    @Test
    public void testUpdateScores(){
        List<Player> players = new ArrayList<>();
        players.add(new Player("Player1"));
        players.add(new Player("Player2"));

        players.get(0).setScore(2);
        int initialScore = players.get(0).getScore();
        Game game = new Game(players, 12);
        game.setPlayerTurn(players.get(0));

        game.setGameWon(true);
        assertEquals(players.get(0).getScore(), initialScore + 1);
    }

    @Test
    public void testTimerOn(){
        List<Player> players = new ArrayList<>();
        players.add(new Player("Player1"));
        players.add(new Player("Player2"));

        Game game = new Game(players, 12);
        game.setPlayerTurn(players.get(0));

        game.setGameWon(true);
        assertTrue(game.isTimerOn());
    }

    @Test
    public void testNumberOfMovesReset(){
        List<Player> players = new ArrayList<>();
        players.add(new Player("Player1"));
        players.add(new Player("Player2"));

        Game game = new Game(players, 12);
        game.setPlayerTurn(players.get(0));
        game.setNumberOfMoves(16513);
        game.setGameWon(true);
        assertEquals(game.getNumberOfMoves(), 0);
    }
}
