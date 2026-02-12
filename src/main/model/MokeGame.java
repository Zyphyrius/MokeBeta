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
    private int movesLeft;
    private boolean attacked;
    private int turnIndex;
    
    // EFFECTS: makes a game with allies and enemies
    //          forms a gameboard with dimensions allies.size() or enemies.size() + 3, depending which is larger
    //          creates a turn order for game based off speed, then set current character to the first character in the turn order
    public MokeGame(ArrayList<Character> allies, ArrayList<Character> enemies) {
        
    }

    // MODIFIES: this
    // EFFECTS: Sets the turn order based off all character's speed
    //          if tied, prioritize first character in list, prioritizing allies before enemies
    public ArrayList<Character> makeTurnOrder() {
        return null;
    }

    // MODIFIES: this
    // EFFECTS: sets current character to next character in turn order and resets moved and attacked and movesLeft
    //          when at end of turn order, return to first
    public void nextTurn() {

    }

    // MODIFIES: this
    // EFFECTS: removes all dead characters from allies, enemies, and turn order, then change turnIndex accordingly
    public void checkDead() {

    }

    // EFFECTS: returns all characters in range that are not itself
    public ArrayList<Character> getInRange(Character c) {
        ArrayList<Character> inRange = new ArrayList<Character>();

        return inRange;
    }

    // REQUIRES: target within range
    // MODIFIES: character
    // EFFECTS: gets current character to attack target, set attacked = false
    public void attackCharacter(Character target) {

    }

    // MODIFIES: character
    // EFFECTS: moves current character in direction if valid, removes one from movesLeft
    //          if no more left, set moved = false
    //          true if valid, false if not
    public Boolean moveCharacter(String direction) {
        return false;
    }

    // EFFECTS: sets moved to false and removes all from movesLeft
    public void endMove() {
        movesLeft = 0;
        moved = false;
    }

    // MODIFIES: this
    // EFFECTS: set current character to character, and set corresponding turnIndex
    public void setCurrentCharacter(Character c) {

    }

    // EFFECTS: returns turn order index
    public int getTurnIndex() {
        return turnIndex;
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

    // EFFECTS: returns gameboard
    public Gameboard getGameboard() {
        return gameboard;
    }

    // EFFECTS: returns if attacked
    public Boolean getAttacked() {
        return attacked;
    }

    // EFFECTS: returns if moved
    public Boolean getMoved() {
        return moved;
    }

    // EFFECTS: returns moves left
    public int getMovesLeft() {
        return movesLeft;
    }
    
}
