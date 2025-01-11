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

package it.unicam.cs.mpmgc.formula1.api.game;

import it.unicam.cs.mpmgc.formula1.api.players.*;
import it.unicam.cs.mpmgc.formula1.api.utils.FileIO;
import it.unicam.cs.mpmgc.formula1.api.utils.Position;
import it.unicam.cs.mpmgc.formula1.api.track.Track;
import it.unicam.cs.mpmgc.formula1.api.track.TrackRenderer;

import java.util.ArrayList;
import java.util.List;

/**
 * This class setups the game track and players to be ready for use.
 */
public class GameSetup{

    private final List<Car> players;
    private int initialPlayerRow;
    private Track track;
    private final FileIO fileIO;
    private final TrackRenderer trackRenderer;
    private static final int INITIAL_PLAYER_COLUMN = 1;

    /**
     * Creates an instance of GameSetup, and starts an Arraylist for players data.
     */
    public GameSetup() {
        this.players = new ArrayList<>();
        this.initialPlayerRow = 1;
        this.fileIO = new FileIO();
        this.trackRenderer = new TrackRenderer();
    }

    /**
     * Loads the track and players data, and initialize them, then it renders the game to be ready.
     */
    public void setupGame(String players, String track){
        loadTrackAndPlayers(players, track);
        initializeTrack();
        initializePlayers();
        renderGame();
    }

    /**
     * Read and Parse track and player data from the given text files using FileIO methods.
     */
    public void loadTrackAndPlayers(String players, String track){
        List<String> trackLines = fileIO.readFile(track);
        List<String> playerLines = fileIO.readFile(players);

        fileIO.parseTrack(trackLines);
        fileIO.parsePlayers(playerLines);
    }

    /**
     * Checks if the track is not null before initializing it.
     */
    public void checkInitializedTrack(){
        if (track == null){
            throw new IllegalArgumentException("Track must be initialized before initializing players.");
        }
    }

    /**
     * Initializes the track by making a new instance of Track
     * with rows and columns dimensions obtained from the read track file in FileIO.
     */
    public void initializeTrack(){
        int[] dimensions = fileIO.loadTrack();
        this.track = new Track(dimensions[0], dimensions[1]);
        track.createTrack(fileIO.getTrackLines());
    }

    /**
     * Initializes the players by getting the split players data from FileIO,
     * and passing them to createAndAddPlayers() method.
     * It respects a maximum number of players obtained from the same players file.
     * IT CHECKS FIRST IF THE TRACK IS INITIALIZED.
     */
    public void initializePlayers() {
        checkInitializedTrack();
        List<String[]> playerData = fileIO.loadPlayers();
        int maxPlayers = fileIO.getMaxPlayers();

        for (String[] pair : playerData){
            if (players.size() >= maxPlayers){
                System.err.println("Max players reached. Skipping additional players");
                break;
            }
            createAndAddPlayers(pair);
        }
    }

    /**
     * It splits the player data into type and name, and in base of the type
     * it creates a new Car instance with its own moving strategy.
     * Then it updates its position to the available starting position,
     * and adds the Car to the players arrayList
     * @param playerData the player data which contains the name and type of car.
     */
    public void createAndAddPlayers(String[] playerData){
        Car player ;
        String playerType = playerData[0];
        String playerName = playerData[1];
        switch (playerType) {
            case "HardBot"  : player = new Car(playerName, new HardBotStrategy(track));     break;
            case "EasyBot"  : player = new Car(playerName, new EasyBotStrategy(track));     break;
            case "Human": player = new Car(playerName, new HumanMovementStrategy(track));   break;
            default     : System.err.println(playerType + ": Type is not Bot/Human -- WILL BE SKIPPED.");return;
        }
        player.updatePosition(new Position(initialPlayerRow, INITIAL_PLAYER_COLUMN));
        players.add(player);
        initialPlayerRow++;
    }

    /**
     * Places every player in its own position on the track matrix, then displays the track.
     */
    public void renderGame(){
        for (iCar player : players){
            trackRenderer.placePlayer(player, track);
        }
        trackRenderer.displayTrack(track);
    }

    /**
     * Gets the players list.
     * @return the players list.
     */
    public List<Car> getPlayers(){return players;}

    /**
     * Gets the track.
     * @return the track.
     */
    public Track getTrack(){ return track; }

    /**
     * Gets the trackLines list of strings.
     * @return the trackLines list of strings.
     */
    public List<String> getTrackLines(){
        return fileIO.getTrackLines();
    }


}
