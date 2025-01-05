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

import it.unicam.cs.mpmgc.formula1.game.GamePlay;
import it.unicam.cs.mpmgc.formula1.game.GameSetup;
import it.unicam.cs.mpmgc.formula1.players.Car;
import it.unicam.cs.mpmgc.formula1.players.iCar;
import it.unicam.cs.mpmgc.formula1.track.Track;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameSetupAndPlayTest {

    GameSetup setup;

    @BeforeEach
    public void setUp(){
        setup = new GameSetup();
        setup.setupGame();
    }

    @Test
    public void TestGameInitializeTrack(){
        Track track = setup.getTrack();
        assertNotNull(track);
        assertEquals(18, track.getRows());
        assertEquals(20, track.getColumns());
    }

    @Test
    public void TestGameInitializePlayers(){
        List<Car> players = setup.getPlayers();
        assertEquals(3, players.size());
        assertEquals("bot1", players.get(0).getName());
        assertEquals("bot2", players.get(1).getName());
        assertEquals("player1", players.get(2).getName());
    }

    @Test
    public void TestGamePlayWinnerDetection(){
        GamePlay game = new GamePlay(setup);

        iCar player1 = setup.getPlayers().get(0);
        iCar player2 = setup.getPlayers().get(1);
        player1.updatePosition(setup.getTrack().getFinishLine().get(0)); // put player1 at a final position

        assertTrue(game.checkWinner(player1));
        assertFalse(game.checkWinner(player2));
    }

}