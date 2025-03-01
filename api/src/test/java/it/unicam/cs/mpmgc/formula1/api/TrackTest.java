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
import it.unicam.cs.mpmgc.formula1.api.players.HumanMovementStrategy;
import it.unicam.cs.mpmgc.formula1.api.track.Track;
import it.unicam.cs.mpmgc.formula1.api.track.TrackRenderer;
import it.unicam.cs.mpmgc.formula1.api.utils.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class TrackTest {

    private Track track;

    @BeforeEach
    public void trackSetUp(){
        List<String> trackLines = new ArrayList<>();
        trackLines.add("########");
        trackLines.add("#......#");
        trackLines.add("#....__#");
        trackLines.add("########");

        track = new Track(4, 8);
        track.createTrack(trackLines);
    }

    @Test
    public void testCreateTrack(){
        char[][] trackMatrix = track.getTrack();

        assertEquals(4, track.getRows());       // rows
        assertEquals(8, track.getColumns());    // columns
        assertNotNull(track.getTrack());
        assertEquals('#', trackMatrix[0][0]);
        assertEquals('.', trackMatrix[1][2]);
        assertEquals('_', trackMatrix[2][5]);
    }

    @Test
    public void testExceptionsCreateTrack(){
        assertThrows(IllegalArgumentException.class,
                () -> track.createTrack(null), "Null Track");
    }

    @Test
    public void testCheckValidMove(){
        Position validMove = new Position(1,1);
        assertTrue(track.checkValidMove(validMove));
    }

    @Test
    public void testInvalidMove(){
        Position invalidMove = new Position(10,1);
        assertFalse(track.checkValidMove(invalidMove));
    }

    @Test
    public void testInvalidMoveForObstacle(){
        Position validMoveButAnotherBotPresent = new Position(3,3);
        assertFalse(track.checkValidMove(validMoveButAnotherBotPresent));
    }

    @Test
    public void testFinishLinePosition(){
        List<Position> finishLine = track.getFinishLine();

        assertEquals(2, finishLine.size());
        assertTrue(finishLine.contains(new Position(2,5)));
        assertTrue(finishLine.contains(new Position(2,6)));
    }

    @Test
    public void testPlaceAndClearPlayerPosition(){
        TrackRenderer trackRenderer = new TrackRenderer();
        Car player = new Car("player1", new HumanMovementStrategy(track));
        player.updatePosition(new Position(1, 1));

        trackRenderer.placePlayer(player, track);
        assertEquals('P', track.getTrack()[1][1]);

        trackRenderer.clearPlayerPosition(player, track);
        assertEquals('.', track.getTrack()[1][1]);
    }

}