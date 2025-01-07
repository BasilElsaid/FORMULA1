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

public interface iMovementStrategy {

    /**
     * finds the position where the car will move to and moves it.
     *
     * @param currentPosition the current position of the car.
     */
    void move(Position currentPosition);

    /**
     * sets the next direction by input for user or by strategy steps for bots.
     */
    void setNextDirection();

    /**
     * sets the speed by keeping direction for users, or randomly for bots.
     */
    void setSpeed();

    /**
     * calculates the new position.
     * @param currentPosition the position of test.
     * @return the new position.
     */
    Position calculateNextPosition(Position currentPosition);

    /**
     * @return the car speed.
     */
    int getSpeed();

    /**
     * @return the car next direction.
     */
    Directions getNextDirection();

    /**
     * connects the car instance with its movement strategy.
     * @param car the car to be connected to this movement strategy.
     */
    void setCarOwner(iCar car);
}
