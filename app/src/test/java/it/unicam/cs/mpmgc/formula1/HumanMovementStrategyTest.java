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

package it.unicam.cs.mpmgc.formula1;

import it.unicam.cs.mpmgc.formula1.players.Car;
import it.unicam.cs.mpmgc.formula1.players.Directions;
import it.unicam.cs.mpmgc.formula1.players.HumanMovementStrategy;
import it.unicam.cs.mpmgc.formula1.players.iCar;
import it.unicam.cs.mpmgc.formula1.track.Track;
import it.unicam.cs.mpmgc.formula1.utils.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;

import static org.junit.jupiter.api.Assertions.*;

class HumanMovementStrategyTest {

    private Track track;
    private HumanMovementStrategy humanStrategy;
    private iCar humanCar;

    @BeforeEach
    public void trackSetUp(){
        track = new Track(5, 6);
        humanStrategy = new HumanMovementStrategy(track);
        humanCar = new Car("Human1", humanStrategy);
    }

    @Test
    public void TestCheckValidMove(){
        track.getTrack()[1][1] = '.';
        Position validMove = new Position(1,1);
        assertTrue(track.checkValidMove(validMove));
    }

    @Test
    public void TestInvalidMove(){
        Position invalidMove = new Position(10,1);  // out of bounders
        assertFalse(track.checkValidMove(invalidMove));
    }

    @Test
    public void TestInvalidMoveForObstacle(){
        track.getTrack()[3][3] = 'B';   // inside track, but there is another bot there
        Position validMoveBotPresent = new Position(3,3);
        assertFalse(track.checkValidMove(validMoveBotPresent));
    }

    @Test
    public void TestSetNextDirection(){
        track.getTrack()[4][5] = '.';
        System.setIn(new ByteArrayInputStream("W\n".getBytes()));
        humanStrategy.move(new Position(5,5));
        assertEquals(Directions.UP, humanStrategy.getNextDirection());

        track.getTrack()[4][4] = '.';
        System.setIn(new ByteArrayInputStream("A\n".getBytes()));
        humanStrategy.move(new Position(4,5));
        assertEquals(Directions.LEFT, humanStrategy.getNextDirection());
    }

    @Test
    public void TestSpeedIncrease(){
        track.getTrack()[4][5] = '.';
        System.setIn(new ByteArrayInputStream("W\n".getBytes()));
        humanStrategy.move(new Position(5,5));
        assertEquals(1, humanStrategy.getSpeed());

        track.getTrack()[3][5] = '.';
        System.setIn(new ByteArrayInputStream("W\n".getBytes()));   // same direction so increase speed
        humanStrategy.move(new Position(4,5));
        assertEquals(2, humanStrategy.getSpeed());
    }



}