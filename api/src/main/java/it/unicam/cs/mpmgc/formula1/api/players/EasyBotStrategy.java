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

/**
 * This class implements a movement strategy for an EASY bot controlled car.
 * The bot follows a predefined logic to move on the track.
 * It cycles through the directions, and it can stop in some turns if the next move is invalid.
 * It moves on constant speed which is 1.
 */
public class EasyBotStrategy implements iMovementStrategy{

    private Directions nextDirection;
    private int speed;
    private final Track track;
    private iCar botCar;

    /**
     * Creates a new instance of the bot car associated with the track.
     * It sets the starting speed to 1, and starting direction to Right.
     * @param track the track on which the car moves.
     */
    public EasyBotStrategy(Track track){
        this.nextDirection = Directions.RIGHT;
        this.speed = 1;
        this.track = track;
    }

    /**
     * The EASY Bot calculates the next position, and check if it is valid.
     * If it is not valid, it does not move this turn, but calculates the direction of the next one.
     * @param currentPosition the current position of the car.
     */
    @Override
    public void move(Position currentPosition) {
        Position newPos = calculateNextPosition(currentPosition);

        if (!track.checkValidMove(newPos)){
            setNextDirection();
            return;
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
     * For EASY Bot, speed is always 1.
     */
    @Override
    public void setSpeed() {
        speed = 1;
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
