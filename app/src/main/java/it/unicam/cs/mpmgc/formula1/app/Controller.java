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
import it.unicam.cs.mpmgc.formula1.api.utils.Position;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.io.IOException;

/**
 * Controller class for JavaFX.
 */
public class Controller {
    
    private Stage stage;

    @FXML
    private GridPane trackGrid;
    @FXML
    private Label finalResult;
    @FXML
    private Button exitButton;

    private static final int CELL_SIZE = 20;

    private GameSetup gameSetup;
    private GamePlay gamePlay;

    private boolean gameOver = false;
    private Timeline botTimeline;

    /**
     * Initializes the controller by setting up the controller and its components.
     */
    public void initialize(){
        setup();
    }

    /**
     * Prepares the game by initializing GameSetup, GamePlay. track and players.
     */
    private void setup(){
        gameSetup = new GameSetup();
        gameSetup.setupGame();
        gamePlay = new GamePlay(gameSetup);
    }

    /**
     * Switches to the game scene, initializes the track, and sets ready the bots and user.
     * @param event the ActionEvent triggered by the user to start the game.
     * @throws IOException if there is an issue loading the FXML file.
     */
    @FXML
    public void switchToGameScene(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/gameScene.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);

        setupBotMovement();
        keyHandler(scene);

        trackGrid = (GridPane) root.lookup("#trackGrid");
        finalResult = (Label) root.lookup("#finalResult");
        exitButton = (Button) root.lookup("#exitButton");

        displayTrack();

        stage.setScene(scene);
        stage.show();
    }

    /**
     * Exits the game when finished.
     */
    @FXML
    private void exitGame(){
        if (stage != null){
            stage.close();
        }
        else {
            System.exit(0);
        }
    }

    /**
     * Displays the track grid on the screen by rendering the track matrix.
     */
    private void displayTrack(){
        trackGrid.getChildren().clear();

        for (int row = 0; row < gameSetup.getTrackLines().size(); row++){
            String line = gameSetup.getTrackLines().get(row);
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
        for (Car car : gameSetup.getPlayers()){
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
     * Checks if the player won after each move, and updates the track grid.
     * @param direction the direction in which the player car is moving.
     */
    private void movePlayer(Directions direction) {
        for (Car car : gameSetup.getPlayers()){
            if (car.getMovementStrategy() instanceof HumanMovementStrategy){
                int newRow = car.getCurrentPosition().getRow();
                int newCol = car.getCurrentPosition().getColumn();

                switch (direction) {
                    case UP -> newRow--;
                    case DOWN -> newRow++;
                    case LEFT -> newCol--;
                    case RIGHT -> newCol++;
                }

                if (gameSetup.getTrack().checkValidMove(new Position(newRow, newCol))) {
                    car.updatePosition(new Position(newRow, newCol));
                    if (gamePlay.checkWinner(car)) {
                        gameWon();
                    } else {
                        displayTrack();
                    }
                }
            }
        }
    }

    /**
     * Moves the bot cars based on their strategies.
     * Checks if a bot won after each move.
     */
    private void moveBots() {
        for (Car car : gameSetup.getPlayers()){
            if (!(car.getMovementStrategy() instanceof HumanMovementStrategy)){
                if (gameOver){
                    return;
                }
                car.getMovementStrategy().move(car.getCurrentPosition());
                if (gamePlay.checkWinner(car)) {
                    gameLost();
                }
            }
        }
        displayTrack();
    }


    /**
     * Sets up a timeLine for bot movement with a move every 250 milliSeconds.
     * The timeLine repeats indefinitely, until a bot or human win, and it stops.
     */
    private void setupBotMovement() {
        botTimeline = new Timeline(new KeyFrame(Duration.millis(250), event -> moveBots()));
        botTimeline.setCycleCount(Timeline.INDEFINITE);
        botTimeline.play();
    }

    /**
     * When the User loses the game, this method stops bots timeLine and displays a lose message.
     * It also shows the exit button.
     */
    private void gameLost() {
        if (gameOver) return;
        gameOver = true;

        finalResult.setText("Game Over! You Lost.");
        finalResult.setTextFill(Color.RED);

        exitButton.setVisible(true);
    }

    /**
     * When the User wins the game, this method stops bots timeLine and displays a win message.
     * It also shows the exit button.
     */
    private void gameWon() {
        if (botTimeline != null) botTimeline.stop();

        finalResult.setText("Congratulations! You Won.");
        finalResult.setTextFill(Color.GREEN);

        exitButton.setVisible(true);
    }

}