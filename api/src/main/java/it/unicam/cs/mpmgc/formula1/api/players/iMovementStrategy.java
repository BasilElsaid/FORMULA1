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
 * This interface defines a contract for movement strategies used by cars on track.
 * Movement strategies can be Human controlled OR bot controlled.
 */
public interface iMovementStrategy {

    /**
     * Determines the next position for the car, and moves it to that position.
     * @param currentPosition the current position of the car.
     */
    void move(Position currentPosition);

    /**
     * Sets the next direction of the car based on User input OR Bot strategy.
     */
    void setNextDirection();

    /**
     * Sets the speed of the car.
     */
    void setSpeed();

    /**
     * Calculates the new position of the car, based on its current position and direction.
     * @param currentPosition the current position of the car.
     * @return the calculated new position.
     */
    Position calculateNextPosition(Position currentPosition);

    /**
     * Gets the speed of the car.
     * @return the car speed.
     */
    int getSpeed();

    /**
     * Gets the next direction of the car.
     * @return the car next direction.
     */
    Directions getNextDirection();

    /**
     * Associates a car instance with a movement strategy.
     * After creating the strategy instance we assign it to the car.
     * @param car the car to be associated to the movement strategy.
     */
    void setCarOwner(iCar car);
}

