package model;

import java.util.ArrayList;

// Represents a board with tiles of dimensionns rowLength and columnLength
public class Gameboard {
    private ArrayList<Tile> tiles;
    private int rowLength;
    private int columnLength;

    // REQUIRES: rowLength and columnLength > 1
    // EFFECTS: creates a gameboard with inputted dimensions,
    //          and creates a list of tiles corresponding to the board dimensions
    public Gameboard(int rowLength, int columnLength) {
        tiles = new ArrayList<Tile>();
        this.rowLength = rowLength;
        this.columnLength = columnLength;
        for (int row = 0; row < rowLength; row++) {
            for (int column = 0; column < columnLength; column++) {
                tiles.add(new Tile());
            }
        }
    }

    // REQUIRES: x and y >= 0 and < rowLength and columnLength
    // EFFECTS: finds the tile corresponding to the x,y coordinates
    //          assuming the top left is (0,0)
    public Tile findTile(int x, int y) {
        int tile = 0;
        for (int i = 0; i < x + y * rowLength; i++) {
            tile++;
        }
        return tiles.get(tile);
    }

    // EFFECTS: returns true if given tile is traversible/exists, otherwise false
    public Boolean validTile(int x, int y) {
        if (x < rowLength & y < columnLength & x >= 0 & y >= 0) {
            if (findTile(x, y).getCharacter() == null) {
                return true;
            }
        }
        return false;
    }

    // REQUIRES: x and y >= 0 and <= rowLength and columnLength, tile is valid
    // MODIFIES: Tile
    // EFFECTS: places a character on a given tile at coordinates (x,y) 
    //          and removes character from all other tiles with that character
    public void placeCharacter(int x, int y, Character character) {
        for (Tile t : tiles) {
            if (t.getCharacter() == character) {
                t.removeCharacter();
            }
        }
        findTile(x, y).setCharacter(character);
    }

    // MODIFIES: Tile
    // EFFECTS: filters the tiles by removing all dead characters
    public void clearDeadCharacterTiles() {
        for (Tile t : tiles) {
            if (t.getCharacter() != null) {
                if (t.getCharacter().isDead()) {
                    t.setCharacter(null);
                }
            }
        }
    }

    public ArrayList<Tile> getTiles() {
        return tiles;
    }

    public int getColumnLength() {
        return columnLength;
    }

    public int getRowLength() {
        return rowLength;
    }
}
