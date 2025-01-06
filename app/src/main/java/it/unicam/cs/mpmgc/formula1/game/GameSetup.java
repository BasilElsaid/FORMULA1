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
 */

package it.unicam.cs.mpmgc.formula1.game;

import it.unicam.cs.mpmgc.formula1.utils.FileIO;
import it.unicam.cs.mpmgc.formula1.utils.Position;
import it.unicam.cs.mpmgc.formula1.players.iCar;
import it.unicam.cs.mpmgc.formula1.players.BotMovementStrategy;
import it.unicam.cs.mpmgc.formula1.players.Car;
import it.unicam.cs.mpmgc.formula1.players.HumanMovementStrategy;
import it.unicam.cs.mpmgc.formula1.track.Track;
import it.unicam.cs.mpmgc.formula1.track.TrackRenderer;

import java.util.ArrayList;
import java.util.List;

public class GameSetup{

    private final List<Car> players;
    private int playerIndex;
    private Track track;
    private final FileIO fileIO;
    private final TrackRenderer trackRenderer;
    private static final int INITIAL_PLAYER_COLUMN = 1;

    public GameSetup() {
        this.players = new ArrayList<>();
        this.playerIndex = 1;
        this.fileIO = new FileIO();
        this.trackRenderer = new TrackRenderer();
    }

    public void setupGame(){
        loadTrackAndPlayers();
        initializeTrack();
        initializePlayers();
        renderGame();
    }

    public void loadTrackAndPlayers(){
        List<String> trackLines = fileIO.readAndParseFile("trackFormat.txt");
        List<String> playerLines = fileIO.readAndParseFile("playersFormat.txt");

        fileIO.parseTrack(trackLines);
        fileIO.parsePlayers(playerLines);
    }

    public void checkInitializedTrack(){
        if (track == null){
            throw new IllegalArgumentException("Track must be initialized before initializing players.");
        }
    }

    public void initializeTrack(){
        int[] dimensions = fileIO.loadTrack();
        this.track = new Track(dimensions[0], dimensions[1]);
        track.createTrack(fileIO.getTrackLines());
    }

    public void initializePlayers() {
        checkInitializedTrack();
        List<String[]> playerData = fileIO.loadPlayers();
        for (String[] pair : playerData){
            createAndAddPlayers(pair);
        }
    }

    public void createAndAddPlayers(String[] playerData){
        Car player ;
        String playerType = playerData[0];
        String playerName = playerData[1];
        switch (playerType) {
            case "Bot"  : player = new Car(playerName, new BotMovementStrategy(track));     break;
            case "Human": player = new Car(playerName, new HumanMovementStrategy(track));   break;
            default     : System.err.println(playerType + ": Type is not Bot/Human -- WILL BE SKIPPED.");return;
        }
        player.updatePosition(new Position(playerIndex, INITIAL_PLAYER_COLUMN));
        players.add(player);
        playerIndex++;
    }

    public void renderGame(){
        for (iCar player : players){
            trackRenderer.placePlayer(player, track);
        }
        trackRenderer.displayTrack(track);
    }

    public List<Car> getPlayers(){return players;}

    public Track getTrack(){ return track; }

    public List<String> getTrackLines(){
        return fileIO.getTrackLines();
    }


}
