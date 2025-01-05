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

package it.unicam.cs.mpmgc.formula1.players;

import it.unicam.cs.mpmgc.formula1.track.Track;
import it.unicam.cs.mpmgc.formula1.utils.ConsoleMessages;
import it.unicam.cs.mpmgc.formula1.utils.Position;

import java.util.Scanner;

/**
 * This class handles creating a Human Car strategy and applying the movement logic of the Human Car.
 */

public class HumanMovementStrategy implements iMovementStrategy {

    private Directions lastDirection;
    private Directions nextDirection;
    private int speed;
    private final Track track;
    private iCar humanCar;
    private final ConsoleMessages messages;

    public HumanMovementStrategy(Track track){
        this.speed = 1;
        this.track = track;
        this.messages = new ConsoleMessages();
    }

    @Override
    public void move(Position currentPosition) {
        setNextDirection();
        setSpeed();

        Position newPos = calculateNextPosition(currentPosition);
        if (!track.checkValidMove(newPos)){
            messages.invalidMoveMessage();
            return;
        }

        lastDirection = nextDirection;
        humanCar.updatePosition(newPos);
        messages.speedMessage(speed);
    }

    @Override
    public void setNextDirection(){
        Scanner scan = new Scanner(System.in);
        Directions direction = null;

        while (direction == null){
            messages.enterMoveMessage();
            String move = scan.nextLine().toUpperCase();
            switch (move){
                case "W" :  direction = Directions.UP;
                    break;
                case "S" :  direction = Directions.DOWN;
                    break;
                case "D" :  direction = Directions.RIGHT;
                    break;
                case "A" :  direction = Directions.LEFT;
                    break;
                default  :  messages.invalidInputMessage();
            }
        }

        nextDirection = direction;
    }

    @Override
    public void setSpeed(){
        if (lastDirection == nextDirection){
            if (speed < 3) {
                speed++;
            }
        }
        else { speed = 1; }
    }

    @Override
    public Position calculateNextPosition(Position currentPos){
        int newRow = currentPos.getRow();
        int newColumn = currentPos.getColumn();

        switch (nextDirection){
            case UP     : newRow -= speed; break;
            case DOWN   : newRow += speed; break;
            case LEFT   : newColumn -= speed; break;
            case RIGHT  : newColumn += speed; break;
        }
        return new Position(newRow, newColumn);
    }

    @Override
    public int getSpeed(){
        return speed;
    }

    @Override
    public Directions getNextDirection(){ return nextDirection; }

    @Override
    public void setCarOwner(iCar car) {
        this.humanCar = car;
    }

}
