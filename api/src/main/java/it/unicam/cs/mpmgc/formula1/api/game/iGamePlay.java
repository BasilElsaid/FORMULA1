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

import it.unicam.cs.mpmgc.formula1.api.players.iCar;

/**
 * This interface provides the needed methods to handle the flow of the game including starting a game,
 * executing turns, checking for the winner and ending the game.
 */
public interface iGamePlay {

    /**
     * Starts the game.
     */
    void startGame();

    /**
     * Ends the game.
     */
    void endGame();

    /**
     * Lets the given player to do its move.
     * @param player the player which is going to do the move.
     */
    void executeTurn(iCar player);

    /**
     * Checks if the player has won the race, so if he is at a final position.
     * @param player the player to be checked.
     * @return true in case a player has won, false in other case.
     */
    boolean checkWinner(iCar player);

}
