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

import it.unicam.cs.mpmgc.formula1.track.Track;
import it.unicam.cs.mpmgc.formula1.utils.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TrackTest {

    private Track track;

    @BeforeEach
    public void TrackSetUp(){
        List<String> trackLines = new ArrayList<>();
        trackLines.add("########");
        trackLines.add("#......#");
        trackLines.add("#....__#");
        trackLines.add("########");

        track = new Track(4, 8);
        track.createTrack(trackLines);
    }

    @Test
    public void TestCreateTrack(){
        char[][] trackMatrix = track.getTrack();

        assertEquals(4, track.getRows());       // rows
        assertEquals(8, track.getColumns());    // columns
        assertNotNull(track.getTrack());
        assertEquals('#', trackMatrix[0][0]);
        assertEquals('.', trackMatrix[1][2]);
        assertEquals('_', trackMatrix[2][5]);
    }

    @Test
    public void TestExceptionsCreateTrack(){
        assertThrows(IllegalArgumentException.class,
                () -> track.createTrack(null), "Null Track");
    }

    @Test
    public void TestFinishLinePosition(){
        List<Position> finishLine = track.getFinishLine();

        assertEquals(2, finishLine.size());
        assertTrue(finishLine.contains(new Position(2,5)));
        assertTrue(finishLine.contains(new Position(2,6)));
    }

}