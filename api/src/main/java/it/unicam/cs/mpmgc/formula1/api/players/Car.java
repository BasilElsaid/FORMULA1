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

import it.unicam.cs.mpmgc.formula1.api.utils.Position;

/**
 * This class represents a Car object.
 * It implements the iCar interface to provide the necessary behaviour of a car.
 */
public class Car implements iCar {

    private final String name;
    private Position currentPosition;
    private final iMovementStrategy movementStrategy;

    /**
     * creates a car instance with specified name and movement strategy.
     * @param name the car name.
     * @param movementStrategy the car movement strategy which determines the behaviour on track.
     */
    public Car(String name, iMovementStrategy movementStrategy){
        this.name = name;
        this.currentPosition = new Position(0,0);
        this.movementStrategy = movementStrategy;
        this.movementStrategy.setCarOwner(this);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Position getCurrentPosition() {
        return currentPosition;
    }

    @Override
    public iMovementStrategy getMovementStrategy() {
        return movementStrategy;
    }

    @Override
    public void updatePosition(Position newPosition) {
        int row = newPosition.getRow();
        int column = newPosition.getColumn();
        this.currentPosition = new Position(row, column);
    }

}
