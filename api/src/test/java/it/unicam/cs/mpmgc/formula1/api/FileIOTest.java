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

import it.unicam.cs.mpmgc.formula1.api.utils.FileIO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileIOTest {

    private FileIO fileIO;

    @BeforeEach
    public void setUp(){
        fileIO = new FileIO();
        List<String> trackLines = fileIO.readFile("trackFormat.txt");
        List<String> playerLines = fileIO.readFile("playersFormat.txt");

        fileIO.parseTrack(trackLines);
        fileIO.parsePlayers(playerLines);
    }

    @Test
    public void testReadFile(){
        List<String> trackLines = fileIO.getTrackLines();
        assertEquals(16, trackLines.size());
        assertEquals("##############", trackLines.get(0));
        assertEquals("#___######...#", trackLines.get(7));

        List<String> playerLines = fileIO.getPlayerLines();
        assertEquals(3, playerLines.size());
    }

    @Test
    public void testLoadTrack(){
        int[] dimensions = fileIO.loadTrack();
        assertEquals(16, dimensions[0]); // rows
        assertEquals(14, dimensions[1]); // columns
    }

    @Test
    public void testLoadPlayers(){
        List<String> playerLines = fileIO.getPlayerLines();
        assertEquals("EasyBot,bot1", playerLines.get(0));
        assertEquals("HardBot,bot2", playerLines.get(1));
        assertEquals("Human,player1", playerLines.get(2));
    }

    @Test
    public void parseTrackEmptyFile(){
        List<String> trackLines2 = new ArrayList<>();
        assertThrows(IllegalStateException.class,
                () -> fileIO.parseTrack(trackLines2), "TRACK FILE IS EMPTY");
    }

    @Test
    public void parsePlayersMaxPlayersException(){
        List<String> playerLines2 = new ArrayList<>();
        playerLines2.add("bot1, anything"); // WITHOUT THE MAX PLAYERS NUMBER
        assertThrows(IllegalStateException.class,
                () -> fileIO.parsePlayers(playerLines2), "INVALID MAX PLAYERS VALUE");
    }

    @Test
    public void parsePlayersEmptyFile(){
        List<String> playerLines3 = new ArrayList<>();
        assertThrows(IllegalStateException.class,
                () -> fileIO.parsePlayers(playerLines3), "PLAYERS FILE IS EMPTY");
    }



}