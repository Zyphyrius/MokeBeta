package model;

import java.util.ArrayList;

public class Tile {
    private Character character;
    //private ArrayList<String> states;

    // EFFECTS: creates a tile with no character on it
    public Tile() {
        
    }

    // EFFECTS: returns the character on this tile, or null if none
    public Character getCharacter() {
        if (!character.equals(null)) {
            return character;
        }
        return null;
    }

    // EFFECTS: set character on tile to character
    public void setCharacter(Character character) {
        this.character = character;
    }

    // EFFECTS: set character on tile to null
    public void removeCharacter() {
        this.character = null;
    }
}
