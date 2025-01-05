/*
 * MIT License
 *
 * Copyright (c) 2024 Basil Elsaid
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

package it.unicam.cs.mpmgc.formula1.utils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FileIO {

    List<String> trackLines;
    List<String> playerLines;

    public FileIO(){
        trackLines = new ArrayList<>();
        playerLines = new ArrayList<>();
    }


    /**
     * reads the file TrackAndPlayers.txt, saves its data in fileLines,
     * then calls the method parseFile(fileLines).
     */
    public void readAndParseFile(String file){
        try {
            Path filePath = Path.of(getClass().getClassLoader().getResource(file).toURI());
            List<String> fileLines = Files.readAllLines(filePath);
            parseFile(fileLines);
        } catch (Exception e){
            throw new RuntimeException("Error reading the file", e);
        }
    }

    /**
     * it divides the data into track Data and players Data,
     * each piece is saved into its specific ArrayList defined above,
     * which are "trackLines and playerLines"
     * @param fileLines the data from TrackAndPlayers.txt file
     */
    public void parseFile(List<String> fileLines){
        trackLines.clear();
        playerLines.clear();

        for (String line : fileLines){
            if(line.contains("#") || line.contains(".")){
                trackLines.add(line);
            }
            else {
                playerLines.add(line);
            }
        }
    }

    /**
     * it calculates the dimensions of the track in rows and columns,
     * then returns them as int[].
     * @return the track dimensions.
     */
    public int[] loadTrack(){
        if (trackLines.isEmpty()){
            throw new IllegalStateException("Track Data is not loaded.");
        }
        int rows = trackLines.size();
        int columns = trackLines.get(0).length();
        return new int[]{rows, columns};
    }

    /**
     * @return trackLines arrayList.
     */
    public List<String> getTrackLines(){
        return trackLines;
    }

    /**
     * it divides the player data into 2 parts, the first is the player type,
     * and the second is player name, then it collects them in an arrayList and returns it.
     * @return arrayList that contains players split data.
     */
    public List<String[]> loadPlayers(){
        List<String[]> playerData = new ArrayList<>();
        for (String line : playerLines) {
            String[] parts = line.split(",");
            if (parts.length != 2) {
                System.err.println("Invalid player data format: " + line);
                continue;
            }
            String type = parts[0].trim();
            String name = parts[1].trim();
            playerData.add(new String[]{type, name});
        }
        return playerData;
    }

    /**
     * @return trackLine arrayList.
     */
    public List<String> getPlayerLines(){
        return playerLines;
    }

}
