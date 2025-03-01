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

package it.unicam.cs.mpmgc.formula1.api.utils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to read data from text files and parse it.
 */

public class FileIO {

    private final List<String> trackLines;
    private final List<String> playerLines;
    private int maxPlayers;

    /**
     * creates a new instance of FileIO, initializing the used variables.
     */
    public FileIO(){
        trackLines = new ArrayList<>();
        playerLines = new ArrayList<>();
        maxPlayers = 0;
    }


    /**
     * reads a file, and saves its data in a fileLines variable.
     * @param file the name of the file to read.
     * @return fileLines variable that contains the read data.
     * @throws RuntimeException if the file can't be found or read.
     */
    public List<String> readFile(String file){
        List<String> fileLines;
        try {
            Path filePath = Path.of(getClass().getClassLoader().getResource(file).toURI());
            fileLines = Files.readAllLines(filePath);
        } catch (Exception e){
            throw new RuntimeException("Error reading the file", e);
        }
        return fileLines;
    }

    /**
     * it adds the data from track txt. file into trackLines arrayList.
     * @param fileLines that contains the data from track txt. file.
     * @throws IllegalStateException if the file is empty.
     */
    public void parseTrack(List<String> fileLines){
        if (fileLines.isEmpty()){
            throw new IllegalStateException("Track file is empty.");
        }

        trackLines.clear();
        for (String line : fileLines){
            trackLines.add(line);
        }
    }

    /**
     * it adds the data from players txt. file into playerLines arrayList.
     * it also sets the maximum number of available players from the same file.
     * @param fileLines that contains the data from players txt. file.
     * @throws IllegalStateException if the file is empty or maximum players value is invalid.
     */
    public void parsePlayers(List<String> fileLines){
        if (fileLines.isEmpty()){
            throw new IllegalStateException("Players file is empty.");
        }

        try {
            maxPlayers = Integer.parseInt(fileLines.get(0).trim());
            fileLines = fileLines.subList(1, fileLines.size()); // Skip the first line
        } catch (NumberFormatException e){
            throw new IllegalStateException("Invalid maximum players value in file.");
        }

        playerLines.clear();
        for (String line : fileLines){
            playerLines.add(line);
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
     * @return a list of strings representing the track layout.
     */
    public List<String> getTrackLines(){
        return trackLines;
    }

    /**
     * it divides the player data into 2 parts, the first is the player type,
     * and the second is player name, then it collects them in playerData variable.
     * @return playerData that contains players split data.
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
     * @return a list of strings representing the player split data.
     */
    public List<String> getPlayerLines(){
        return playerLines;
    }

    /**
     * @return maximum number of available players.
     */
    public int getMaxPlayers(){
        return maxPlayers;
    }

}
