package model;

// Represents a tile on the board with that stores whats on it
public class Tile {
    private Character character;

    // EFFECTS: creates a tile with no character on it
    public Tile() {

    }

    // EFFECTS: returns the character on this tile, or null if none
    public Character getCharacter() {
        if (character != null) {
            return character;
        }
        return null;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }

    // MODIFIES: this
    // EFFECTS: set character on tile to null
    public void removeCharacter() {
        this.character = null;
    }
}
