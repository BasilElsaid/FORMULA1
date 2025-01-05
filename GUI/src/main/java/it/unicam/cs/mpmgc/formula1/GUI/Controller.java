package it.unicam.cs.mpmgc.formula1.GUI;

import it.unicam.cs.mpmgc.formula1.game.GamePlay;
import it.unicam.cs.mpmgc.formula1.game.GameSetup;
import it.unicam.cs.mpmgc.formula1.players.*;
import it.unicam.cs.mpmgc.formula1.track.Track;
import it.unicam.cs.mpmgc.formula1.utils.Position;

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

    public void initialize(){
        setup();
    }

    private void setup(){
        gameSetup = new GameSetup();
        gameSetup.setupGame();
        gamePlay = new GamePlay(gameSetup);

        trackLines = gameSetup.getTrackLines();
        cars = gameSetup.getPlayers();
        track = gameSetup.getTrack();
    }

    // Handle switching to the game scene
    @FXML
    public void switchToGameScene(ActionEvent event) throws IOException {
        // Load the new scene
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/gameScene.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);

        keyHandler(scene);

        // Initialize the track grid and add it to the root
        trackGrid = (GridPane) root.lookup("#trackGrid"); // Ensure trackGrid is properly initialized in the FXML
        displayTrack();  // Draw the track and player

        setupBotMovement();
        // Set and show the scene
        stage.setScene(scene);
        stage.show();
    }

    //-------------//
    //TRACK SECTION//
    //------------//

    // Create and return a GridPane for the track based on the parsed lines
    private void displayTrack(){
        trackGrid.getChildren().clear();  // Clear previous track (if any)

        for (int row = 0; row < trackLines.size(); row++){
            String line = trackLines.get(row);
            for (int column = 0; column < line.length(); column++){
                char cellType = line.charAt(column);

                Rectangle cell = new Rectangle(CELL_SIZE, CELL_SIZE);

                // Set color based on cell type
                switch (cellType){
                    case '#' : cell.setFill(Color.BLACK); break;  // Track wall
                    case '.' : cell.setFill(Color.LIGHTGRAY); break;  // Empty space
                    case '_' : cell.setFill(Color.YELLOW); break;  // Special space (like checkpoints)
                    default  : cell.setFill(Color.WHITE); break;  // Default empty cell
                }

                trackGrid.add(cell, column, row);  // Add the cell to the GridPane
            }
        }
        placeRacers();
    }

    private void placeRacers(){
        Color color;
        for (Car car : cars){
            if (car.getMovementStrategy() instanceof HumanMovementStrategy){
                color = Color.RED;
            }
            else {
                if (car.getName().equals("bot1")){
                    color = Color.BLUE;
                }
                else {
                    color = Color.GREEN;
                }
            }
            Rectangle player = new Rectangle(CELL_SIZE, CELL_SIZE, color);
            trackGrid.add(player, car.getCurrentPosition().getColumn(), car.getCurrentPosition().getRow());
        }
    }

    //--------------//
    //PLAYER SECTION//
    //-------------//

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
                    break; // Ignore other keys
            }

            if (direction != null) {
                movePlayer(direction);
            }
        });
    }

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
            } else if (botCar1.getCurrentPosition().equals(player1.getCurrentPosition())
                    || botCar2.getCurrentPosition().equals(player1.getCurrentPosition())) {
                gameLost();
            } else {
                displayTrack();
            }
        }
    }

    //-----------//
    //BOT SECTION//
    //----------//

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

    private void setupBotMovement() {
        botTimer = new Timer(true); // Daemon timer to stop when the application closes
        botTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> moveBots());
            }
        }, 0, 250); // Schedule with a delay of 0ms and period of 250ms
    }

    //------------//
    //GAME SECTION//
    //-----------//

    private void gameLost() {
        if (gameOver) return;
        gameOver = true;
        if (botTimer != null) botTimer.cancel();

        Platform.runLater(() -> {
            showAlert("Game Over", "You Lost!", "A bot reached the finish line or caught you.", Alert.AlertType.ERROR);
            stage.close();
        });
    }

    private void gameWon() {
        if (botTimer != null) botTimer.cancel();

        Platform.runLater(() -> {
            showAlert("Game Over", "Congratulations!", "You have reached the finish line and won the game!", Alert.AlertType.INFORMATION);
            stage.close();
        });
    }

    private void showAlert(String title, String header, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }


}