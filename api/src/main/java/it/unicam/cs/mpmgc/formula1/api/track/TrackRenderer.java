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

package it.unicam.cs.mpmgc.formula1.api.track;

import it.unicam.cs.mpmgc.formula1.api.players.iCar;

/**
 * This class provides methods for rendering and updating the track.
 */
public class TrackRenderer {

    /**
     * Creates a new instance of the class.
     */
    public TrackRenderer(){};

    /**
     * Displays the whole track in console.
     * @param track the track to be displayed.
     * @throws IllegalArgumentException if the provided track is null.
     */
    public void displayTrack(iTrack track){
        if (track == null){
            throw new IllegalArgumentException("Track can't be null.");
        }
        char[][] trackFormat = track.getTrack();
        for (char[] row : trackFormat) {
            for (char cell : row) {
                System.out.print(cell);
            }
            System.out.println();
        }
    }

    /**
     * Places a player on the track by placing the player's name first letter in Uppercase
     * in its position on the track matrix.
     * @param player the player to be placed.
     * @param track the track where the player is placed.
     */
    public void placePlayer(iCar player, iTrack track) {
        int row = player.getCurrentPosition().getRow();
        int column = player.getCurrentPosition().getColumn();
        track.getTrack()[row][column] = player.getName().toUpperCase().charAt(0);
    }

    /**
     * Clears the player position on track matrix by changing its letter with '.' character
     * to indicate a free position on track matrix.
     * This is used before making the player's next move.
     * @param player the player position to be reset into '.',
     * @param track the track where we apply the changes.
     */
    public void clearPlayerPosition(iCar player, iTrack track) {
        int row = player.getCurrentPosition().getRow();
        int column = player.getCurrentPosition().getColumn();
        track.getTrack()[row][column] = '.';
    }

}
