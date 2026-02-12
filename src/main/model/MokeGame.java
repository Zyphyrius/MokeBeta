package model;

import java.util.ArrayList;

// an instance of the game that handles all characters and turns
public class MokeGame {
    private static ArrayList<Character> allies;
    private static ArrayList<Character> enemies;
    private static ArrayList<Character> turnOrder;
    private Character currentCharacter;
    private Gameboard gameboard;
    private boolean moved;
    private int movesLeft;
    private boolean attacked;
    private int turnIndex;
    
    // REQUIRES: allies.size() and enemies.size() > 0
    // EFFECTS: makes a game with allies and enemies
    //          forms a gameboard with dimensions allies.size() or enemies.size() + 3, depending which is larger
    //          creates a turn order for game based off speed, then set current character to the first character in the turn order
    public MokeGame(ArrayList<Character> allies, ArrayList<Character> enemies) {
        MokeGame.allies = allies;
        MokeGame.enemies = enemies;
        if (allies.size() >= enemies.size()) {
            gameboard = new Gameboard(allies.size() + 3, allies.size() + 3);
        } else {
            gameboard = new Gameboard(enemies.size() + 3, enemies.size() + 3);
        }
        turnOrder = makeTurnOrder();
        turnIndex = 0;
        currentCharacter = turnOrder.get(0);
        moved = false;
        attacked = false;
        movesLeft = currentCharacter.getMove();
    }

    // REQUIRES: allies.size() and enemies.size() > 0
    // MODIFIES: this
    // EFFECTS: Sets the turn order based off all character's speed
    //          if tied, prioritize first character in list, prioritizing allies before enemies
    public ArrayList<Character> makeTurnOrder() {
        ArrayList<Character> newTurnOrder = new ArrayList<Character>();
        ArrayList<Character> allCharacters = new ArrayList<Character>(allies);
        allCharacters.addAll(enemies);
        for (Character cAdd : allCharacters) {
            int index = 0;
            for (Character cCompare : newTurnOrder) {
                if (cAdd.getSpeed() > cCompare.getSpeed()) {
                    break;
                } else {
                    index++;
                }
            }
            newTurnOrder.add(index, cAdd);
        }

        return newTurnOrder;
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

    // EFFECTS: returns first alive character, starting with the current character
    public Character findFirstAlive() {
        return null;
    }

    // REQUIRES: target within range
    // MODIFIES: character
    // EFFECTS: gets current character to attack target, set attacked = true
    public void attackCharacter(Character target) {

    }

    // MODIFIES: character
    // EFFECTS: moves current character in direction if valid, removes one from movesLeft
    //          if no more left, set moved = true
    //          true if valid, false if not
    public Boolean moveCharacter(String direction) {
        return false;
    }

    // EFFECTS: sets moved to true and removes all from movesLeft
    public void endMove() {
        movesLeft = 0;
        moved = true;
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
    public static ArrayList<Character> getAllies() {
        return allies;
    }

    // EFFECTS: returns list of all enemies
    public static ArrayList<Character> getEnemies() {
        return enemies;
    }

    // EFFECTS: returns turn order
    public static ArrayList<Character> getTurnOrder() {
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
