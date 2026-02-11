package model;

import java.util.ArrayList;
import java.util.Scanner;

// an instance of the game that handles all characters and turns
public class MokeGame {
    private ArrayList<Character> allies;
    private ArrayList<Character> enemies;
    private ArrayList<Character> turnOrder;
    private Character currentCharacter;
    private Gameboard gameboard;
    private boolean moved;
    private boolean attacked;
    
    // EFFECTS: makes a game with allies and enemies
    //          forms a gameboard with dimensions allies.size() or enemies.size(), depending which is larger
    //          creates a turn order for game based off speed, then set current character to the first character in the turn order
    public MokeGame(ArrayList<Character> allies, ArrayList<Character> enemies) {
        
    }

    // MODIFIES: this
    // EFFECTS: sets current character to next character in turn order and resets moved and attacked
    public void nextTurn() {

    }

    // EFFECTS: returns all characters in range that are not itself
    public ArrayList<Character> getInRange(Character c) {
        ArrayList<Character> inRange = new ArrayList<Character>();

        return inRange;
    }

    // REQUIRES: target within range
    // MODIFIES: character
    // EFFECTS: gets current character to attack target
    public void attackCharacter(Character target) {

    }

    // EFFECTS: moves current character in direction
    public void moveCharacter(String direction) {

    }

    // MODIFIES: this
    // EFFECTS: Sets the turn order based off all character's speed
    public ArrayList<Character> makeTurnOrder() {
        return null;
    }

    // EFFECTS: returns list of all allies
    public ArrayList<Character> getAllies() {
        return allies;
    }

    // EFFECTS: returns list of all enemies
    public ArrayList<Character> getEnemies() {
        return enemies;
    }

    // EFFECTS: returns turn order
    public ArrayList<Character> getTurnOrder() {
        return turnOrder;
    }

    // EFFECTS: returns current character whose turn it is
    public Character getCurrentCharacter() {
        return currentCharacter;
    }
    


    
}
