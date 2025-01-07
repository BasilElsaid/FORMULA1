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

/**
 * Represents a Position on the track by a pair of coordinates (row, column).
 */

public final class Position {

    private final int row;
    private final int column;

    /**
     * creates a new position with specific coordinates.
     * @param row the row index of the position.
     * @param column the column index of the position.
     */
    public Position(int row, int column){
        this.row = row;
        this.column = column;
    }

    /**
     * @return row index of the position.
     */
    public int getRow() {
        return row;
    }

    /**
     * @return column index of the position.
     */
    public int getColumn() {
        return column;
    }

    /**
     * compares this position to the specified object.
     * two positions are equal if they have the same row and column values.
     * @param o the object to compare with.
     * @return true if the two objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return row == position.row && column == position.column;
    }

    /**
     * @return the hashcode for this position.
     */
    @Override
    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = prime * result + row;
        result = prime * result + column;
        return result;
    }

}
