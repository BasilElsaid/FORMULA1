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

package it.unicam.cs.mpmgc.formula1.utils;

import it.unicam.cs.mpmgc.formula1.players.iCar;

public class ConsoleMessages {

    public ConsoleMessages(){};

    /**
     * displays the welcome message in console.
     */
    public void startGameMessage(){

        System.out.println("Welcome to Formula 1 simulator game!");
    }

    /**
     * displays the end game message in console.
     */
    public void endGameMessage(){
        System.out.println("Thanks for trying my game!");
    }

    /**
     * displays the player's turn message in console.
     * @param player the player which the turn is.
     */
    public void playerTurnMessage(iCar player){
        System.out.println(player.getName() + "'s Turn!");
    }

    /**
     * displays the winner name in console.
     * @param player the player who won the game.
     */
    public void winnerNameMessage(iCar player){
        System.out.println("The winner is : " + player.getName());
    }

    /**
     * displays wrong input message.
     */
    public void invalidInputMessage(){
        System.err.println("Invalid Input, Retry.");
    }

    /**
     * displays enter move message.
     */
    public void enterMoveMessage(){
        System.out.println("Enter your move: W:up, S:down, A:left, D:right");
    }

    /**
     * displays speed increased message.
     */
    public void speedMessage(int speed){
        System.out.println("Your speed is: " + speed);
    }

    /**
     * displays wrong move message.
     */
    public void invalidMoveMessage(){
        System.err.println("Invalid Move, stay at same position.");
    }

}
