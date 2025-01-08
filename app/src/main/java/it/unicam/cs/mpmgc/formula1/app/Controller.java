/*
 * MIT License
 *
 * Copyright (c) 2025 Basil Elsaid
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

package it.unicam.cs.mpmgc.formula1.app;

import it.unicam.cs.mpmgc.formula1.api.game.GamePlay;
import it.unicam.cs.mpmgc.formula1.api.game.GameSetup;
import it.unicam.cs.mpmgc.formula1.api.players.*;
import it.unicam.cs.mpmgc.formula1.api.track.Track;
import it.unicam.cs.mpmgc.formula1.api.utils.Position;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.scene.control.Alert;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Controller class for JavaFX.
 */
public class Controller {


    private Stage stage;

    @FXML
    private GridPane trackGrid;  // FXML GridPane for the track
    private static final int CELL_SIZE = 20;

    private Track track;
    private GameSetup gameSetup;
    private GamePlay gamePlay;

    private List<Car> cars = new ArrayList<>();
    private List<String> trackLines = new ArrayList<>();

    private boolean gameOver = false;
    private Timer botTimer;

    /**
     * Initializes the controller by setting up the controller and its components.
     */
    public void initialize(){
        setup();
    }

    /**
     * Sets up the game by initializing GameSetup, GamePlay. track and players.
     */
    private void setup(){
        gameSetup = new GameSetup();
        gameSetup.setupGame();
        gamePlay = new GamePlay(gameSetup);

        trackLines = gameSetup.getTrackLines();
        cars = gameSetup.getPlayers();
        track = gameSetup.getTrack();
    }

    /**
     * Switches to the game scene, initializes the track, and sets up the game.
     * @param event the ActionEvent triggered by the user to start the game.
     * @throws IOException if there is an issue loading the FXML file.
     */
    @FXML
    public void switchToGameScene(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/gameScene.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);

        keyHandler(scene);

        trackGrid = (GridPane) root.lookup("#trackGrid");
        displayTrack();

        setupBotMovement();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Displays the track grid on the screen by rendering the track matrix.
     */
    private void displayTrack(){
        trackGrid.getChildren().clear();  // Clear previous track (if any)

        for (int row = 0; row < trackLines.size(); row++){
            String line = trackLines.get(row);
            for (int column = 0; column < line.length(); column++){
                char cellType = line.charAt(column);

                Rectangle cell = new Rectangle(CELL_SIZE, CELL_SIZE);

                switch (cellType){
                    case '#' : cell.setFill(Color.BLACK); break;
                    case '.' : cell.setFill(Color.LIGHTGRAY); break;
                    case '_' : cell.setFill(Color.YELLOW); break;
                    default  : cell.setFill(Color.WHITE); break;
                }

                trackGrid.add(cell, column, row);
            }
        }
        placeRacers();
    }

    /**
     * Places the players on the track grid based on their positions.
     */
    private void placeRacers(){
        Color color;
        iMovementStrategy movementStrategy;
        for (Car car : cars){
            movementStrategy = car.getMovementStrategy();
            if (movementStrategy instanceof HumanMovementStrategy){
                color = Color.RED;
            }
            else if (movementStrategy instanceof EasyBotStrategy){
                color = Color.BLUE;
            }
            else {
                color = Color.GREEN;
            }
            Rectangle player = new Rectangle(CELL_SIZE, CELL_SIZE, color);
            trackGrid.add(player, car.getCurrentPosition().getColumn(), car.getCurrentPosition().getRow());
        }
    }

    /**
     * Handles keyboard input from user for controlling the player car.
     * @param scene the scene where the key events are obtained.
     */
    public void keyHandler(Scene scene) {
        scene.setOnKeyPressed(event -> {
            Directions direction = null;

            switch (event.getCode()) {
                case W:
                    direction = Directions.UP;
                    break;
                case S:
                    direction = Directions.DOWN;
                    break;
                case A:
                    direction = Directions.LEFT;
                    break;
                case D:
                    direction = Directions.RIGHT;
                    break;
                default:
                    break;
            }

            if (direction != null) {
                movePlayer(direction);
            }
        });
    }

    /**
     * Moves the player car in the specified direction,
     * checks for collisions or winning conditions, and updates the track grid.
     * @param direction the direction in which the player car is moving.
     */
    private void movePlayer(Directions direction) {
        Car botCar1 = cars.get(0);
        Car botCar2 = cars.get(1);
        Car player1 = cars.get(2);

        int newRow = player1.getCurrentPosition().getRow();
        int newCol = player1.getCurrentPosition().getColumn();

        switch (direction) {
            case UP -> newRow--;
            case DOWN -> newRow++;
            case LEFT -> newCol--;
            case RIGHT -> newCol++;
        }

        if (track.checkValidMove(new Position(newRow, newCol))) {
            player1.updatePosition(new Position(newRow, newCol));
            if (gamePlay.checkWinner(player1)) {
                gameWon();
            } else {
                displayTrack();
            }
        }
    }

    /**
     * Moves the bot cars based on their strategies.
     * Checks for collisions or winning conditions after each move.
     */
    private void moveBots() {
        Car botCar1 = cars.get(0);
        Car botCar2 = cars.get(1);

        if (gameOver){
            return;
        }
        botCar1.getMovementStrategy().move(botCar1.getCurrentPosition());
        if (gamePlay.checkWinner(botCar1)) {
            gameLost();
        }
        botCar2.getMovementStrategy().move(botCar2.getCurrentPosition());
        if (gamePlay.checkWinner(botCar2)) {
            gameLost();
        }
        // Refresh the grid to display bot movement
        displayTrack();
    }

    /**
     * Sets up a timer for bot movement.
     */
    private void setupBotMovement() {
        botTimer = new Timer(true); // Daemon timer to stop when the application closes
        botTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> moveBots());
            }
        }, 0, 250);
    }

    /**
     * When the User loses the game, this method stops bots timer and displays an alert with lose message.
     */
    private void gameLost() {
        if (gameOver) return;
        gameOver = true;
        if (botTimer != null) botTimer.cancel();

        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Game Over");
            alert.setHeaderText("You Lost!");
            alert.setContentText("A bot reached the finish line.");
            alert.showAndWait();
            stage.close();
        });
    }

    /**
     * When the User wins the game, this method stops bots timer and displays an alert with win message.
     */
    private void gameWon() {
        if (botTimer != null) botTimer.cancel();

        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Game Over");
            alert.setHeaderText("Congratulations!");
            alert.setContentText("You have reached the finish line and won the game!");
            alert.showAndWait();
            stage.close();
        });
    }

}