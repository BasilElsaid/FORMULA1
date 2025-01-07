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

package it.unicam.cs.mpmgc.formula1.api;

import it.unicam.cs.mpmgc.formula1.api.players.Car;
import it.unicam.cs.mpmgc.formula1.api.players.Directions;
import it.unicam.cs.mpmgc.formula1.api.players.EasyBotStrategy;
import it.unicam.cs.mpmgc.formula1.api.players.HardBotStrategy;
import it.unicam.cs.mpmgc.formula1.api.track.Track;
import it.unicam.cs.mpmgc.formula1.api.utils.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BotStrategyTest {

    Track track;
    HardBotStrategy hardBotStrategy;
    EasyBotStrategy easyBotStrategy;
    Car hardBot;
    Car easyBot;

    @BeforeEach
    public void trackSetUp(){
        List<String> trackLines = new ArrayList<>();
        trackLines.add("########");
        trackLines.add("#......#");
        trackLines.add("#......#");
        trackLines.add("#__....#");
        trackLines.add("########");

        track = new Track(5, 8);
        track.createTrack(trackLines);

        hardBotStrategy = new HardBotStrategy(track);
        easyBotStrategy = new EasyBotStrategy(track);
        hardBot = new Car("Bot1", hardBotStrategy);
        easyBot = new Car("Bot2", easyBotStrategy);

    }

    @Test
    public void testBotInitialDefaultDirection(){
        assertEquals(Directions.RIGHT, hardBotStrategy.getNextDirection());
        assertEquals(Directions.RIGHT, easyBotStrategy.getNextDirection());
    }

    @Test
    public void testEasyBotNextDirection(){
        easyBot.updatePosition(new Position(2, 1));
        easyBot.getMovementStrategy().move(easyBot.getCurrentPosition());
        Position easyBotNextPos = easyBot.getCurrentPosition();

        assertEquals(easyBotNextPos, new Position(2,2));
    }

    @Test
    public void testHardBotNextDirection(){
        hardBot.updatePosition(new Position(1, 1));
        hardBot.getMovementStrategy().move(hardBot.getCurrentPosition());
        Position hardBotNextPos = hardBot.getCurrentPosition();

        boolean expectedPos =
                (hardBotNextPos.getRow() == 1 && hardBotNextPos.getColumn() == 2)   // speed = 1
             || (hardBotNextPos.getRow() == 1 && hardBotNextPos.getColumn() == 3);  // speed = 2

        assertTrue(expectedPos);
    }

    @Test
    public void testEasyBotNextDirectionOnObstacle(){
        easyBot.updatePosition(new Position(1, 6));

        easyBot.getMovementStrategy().move(easyBot.getCurrentPosition());
        Position easyBotNextPos = easyBot.getCurrentPosition();
        assertEquals(easyBotNextPos, new Position(1,6)); //WILL NOT MOVE

        easyBot.getMovementStrategy().move(easyBot.getCurrentPosition());
        Position easyBotNextPos2 = easyBot.getCurrentPosition();
        assertEquals(easyBotNextPos2, new Position(2,6)); //WILL GO DOWN
    }

    @Test
    public void testHardBotNextDirectionOnObstacle(){
        hardBot.updatePosition(new Position(1, 6));

        hardBot.getMovementStrategy().move(hardBot.getCurrentPosition()); // RIGHT IS WALL '#'
        Position hardBotNextPos = hardBot.getCurrentPosition();

        //CHANGES DIRECTION TO DOWN
        boolean expectedPos =
                (hardBotNextPos.getRow() == 2 && hardBotNextPos.getColumn() == 6)   //WITH SPEED = 1
             || (hardBotNextPos.getRow() == 3 && hardBotNextPos.getColumn() == 6);  //WITH SPEED = 2

        assertTrue(expectedPos);
    }

}