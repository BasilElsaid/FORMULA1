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

import it.unicam.cs.mpmgc.formula1.utils.FileIO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileIOTest {

    private FileIO fileIO;

    @BeforeEach
    public void SetUp(){
        fileIO = new FileIO();
        List<String> trackLines = fileIO.readFile("trackFormat.txt");
        List<String> playerLines = fileIO.readFile("playersFormat.txt");

        fileIO.parseTrack(trackLines);
        fileIO.parsePlayers(playerLines);
    }

    @Test
    public void TestReadFile(){
        List<String> trackLines = fileIO.getTrackLines();
        assertEquals(18, trackLines.size());
        assertEquals("####################", trackLines.get(0));
        assertEquals("#______######......#", trackLines.get(8));

        List<String> playerLines = fileIO.getPlayerLines();
        assertEquals(3, playerLines.size());
    }

    @Test
    public void TestLoadTrack(){
        int[] dimensions = fileIO.loadTrack();
        assertEquals(18, dimensions[0]); // rows
        assertEquals(20, dimensions[1]); // columns
    }

    @Test
    public void TestLoadPlayers(){
        List<String> playerLines = fileIO.getPlayerLines();
        assertEquals("Bot,bot1", playerLines.get(0));
        assertEquals("Bot,bot2", playerLines.get(1));
        assertEquals("Human,player1", playerLines.get(2));
    }

}