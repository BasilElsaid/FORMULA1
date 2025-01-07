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

package it.unicam.cs.mpmgc.formula1.api.players;

import it.unicam.cs.mpmgc.formula1.api.track.Track;
import it.unicam.cs.mpmgc.formula1.api.utils.Position;

import java.util.Random;

/**
 * This class implements a movement strategy for an HARD bot controlled car.
 * The bot follows a predefined logic to move on the track.
 *  It cycles through the directions, and do not stop in any turn.
 *  It moves with speed equal to 1 or 2.
 */

public class HardBotStrategy implements iMovementStrategy {

    private Directions nextDirection;
    private int speed;
    private final Track track;
    private iCar botCar;

    /**
     * Creates a new instance of the bot car associated with the track.
     * It sets the starting speed to 1, and starting direction to Right.
     * @param track the track on which the car moves.
     */
    public HardBotStrategy(Track track){
        this.nextDirection = Directions.RIGHT;
        this.speed = 1;
        this.track = track;
    }

    /**
     * The HARD Bot calculates the next position, and check if it is valid.
     * If it is not valid, it keeps trying to calculate the next direction until it finds a valid move.
     * @param currentPosition the current position of the car.
     */
    @Override
    public void move(Position currentPosition) {
        Position newPos = calculateNextPosition(currentPosition);

        while (!track.checkValidMove(newPos)){
            setNextDirection();
            newPos = calculateNextPosition(currentPosition);
        }
        botCar.updatePosition(newPos);
    }

    /**
     * Bot movement logic.
     */
    @Override
    public void setNextDirection() {
        switch (nextDirection){
            case RIGHT -> nextDirection = Directions.DOWN;
            case DOWN -> nextDirection = Directions.LEFT;
            case LEFT -> nextDirection = Directions.UP;
            case UP -> nextDirection = Directions.RIGHT;
        }
    }

    /**
     * For HARD Bot, speed can be 1 or 2.
     */
    @Override
    public void setSpeed() {
        Random random = new Random();
        speed = 1 + random.nextInt(2);
    }

    @Override
    public Position calculateNextPosition(Position currentPos){
        setSpeed();

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
    public Directions getNextDirection(){
        return nextDirection;
    }

    @Override
    public void setCarOwner(iCar car){
        this.botCar = car;
    }

}