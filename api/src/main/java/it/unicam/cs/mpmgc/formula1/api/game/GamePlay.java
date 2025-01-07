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

import it.unicam.cs.mpmgc.formula1.api.utils.ConsoleMessages;
import it.unicam.cs.mpmgc.formula1.api.utils.Position;
import it.unicam.cs.mpmgc.formula1.api.players.iCar;
import it.unicam.cs.mpmgc.formula1.api.track.Track;
import it.unicam.cs.mpmgc.formula1.api.track.TrackRenderer;

import java.util.Scanner;

/**
 * This class is the core class which runs the whole game.
 */
public class GamePlay implements iGamePlay {

    private boolean gameFinished;
    private final GameSetup gameSetup;
    private final Track track;
    private final ConsoleMessages messages;
    private final TrackRenderer trackRenderer;

    /**
     * Creates an instance of GamePlay, setups the track and players from a GameSetup.
     * @param setup the setup of track and players ready to be played on.
     * @throws IllegalArgumentException if setup is null.
     */
    public GamePlay(GameSetup setup){
        if (setup == null){
            throw new IllegalArgumentException("Setup can not be null.")
        }
        this.gameFinished = false;
        this.gameSetup = setup;
        this.track = setup.getTrack();
        this.messages = new ConsoleMessages();
        this.trackRenderer = new TrackRenderer();
    }

    /**
     * Starts the game by a welcome message in console, it executes every player turn,
     * and checks if he won, after every turn it displays the track status in console,
     * when the game ends, it shows and end game message in console, and the winner name.
     */
    @Override
    public void startGame(){
        Scanner scan = new Scanner(System.in);
        messages.startGameMessage();
        while (!gameFinished) {
            for (iCar player : gameSetup.getPlayers()){
                executeTurn(player);
                if (checkWinner(player)){
                    break;
                }
            }
            trackRenderer.displayTrack(track);
        }
        messages.endGameMessage();
        scan.close();
    }

    /**
     * Ends the game, so startGame() method can get out of the while loop.
     */
    @Override
    public void endGame(){
        this.gameFinished = true;
    }

    /**
     * The player turn is in this order:
     * display in console the player name and turn,
     * clear its position on track matrix,
     * move the player using its own strategy,
     * and place the player to its new position on track matrix.
     * @param player the player which is going to do the move.
     */
    @Override
    public void executeTurn(iCar player) {
        messages.playerTurnMessage(player);
        trackRenderer.clearPlayerPosition(player, track);
        player.getMovementStrategy().move(player.getCurrentPosition());
        trackRenderer.placePlayer(player, track);
    }

    /**
     * Checks if player has won the game, by comparing its position to the finish line positions.
     * @param player the player to be checked.
     * @return true if player has won, false otherwise.
     */
    @Override
    public boolean checkWinner(iCar player){
        for (Position position : track.getFinishLine()){
            if (player.getCurrentPosition().equals(position)){
                messages.winnerNameMessage(player);
                endGame();
                return true;
            }
        }
        return false;
    }

}
