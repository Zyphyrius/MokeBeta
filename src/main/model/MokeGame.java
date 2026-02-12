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
    private boolean gameOver;
    private boolean win;
    
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
        gameOver = false;
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
        turnIndex++;
        if (turnIndex >= turnOrder.size()) {
            turnIndex = 0;
        }
        currentCharacter = turnOrder.get(turnIndex);
        moved = false;
        attacked = false;
        movesLeft = currentCharacter.getMove();
    }

    // MODIFIES: this
    // EFFECTS: removes all dead characters from allies, enemies, and turn order, then change turnIndex accordingly, then checks if won/lost
    public void checkDead() {
        Character firstAlive = findFirstAlive();
        AliveFilter aliveFilter = new AliveFilter();
        allies = aliveFilter.characterFilter(allies);
        enemies = aliveFilter.characterFilter(enemies);
        checkGameOver();
        if (!gameOver){
            turnOrder = makeTurnOrder();
            turnIndex = turnOrder.indexOf(firstAlive);
            currentCharacter = turnOrder.get(turnIndex);
        }    
    }

    // EFFECTS: returns first alive character, starting with the current character
    public Character findFirstAlive() {
        int tempTurnIndex = turnIndex;
        for (int i=0; i < turnOrder.size(); i++) {
            if (!turnOrder.get(tempTurnIndex).isDead()) {
                break;
            }
            tempTurnIndex++;
            if (tempTurnIndex >= turnOrder.size()) {
                tempTurnIndex = 0;
            }
        }
        return turnOrder.get(tempTurnIndex);
    }

    // EFFECTS: checks if game is over. over when allies or enemies are all gone
    //          if it is, set gameOver to true and determine if won/lost
    //          if both sides are completely dead, it is a loss
    public void checkGameOver() {
        if (allies.isEmpty()) {
            gameOver = true;
            win = false;
        } else if (enemies.isEmpty()) {
            gameOver = true;
            win = true;
        }
    }

    // REQUIRES: target within range
    // MODIFIES: character
    // EFFECTS: gets current character to attack target, set attacked = true
    public void attackCharacter(Character target) {
        attacked = true;
        currentCharacter.attack(target);
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
        currentCharacter = c;
        turnIndex = turnOrder.indexOf(c);
    }

    public int getTurnIndex() {
        return turnIndex;
    }

    public static ArrayList<Character> getAllies() {
        return allies;
    }

    public static ArrayList<Character> getEnemies() {
        return enemies;
    }

    public static ArrayList<Character> getTurnOrder() {
        return turnOrder;
    }

    public Character getCurrentCharacter() {
        return currentCharacter;
    }

    public Gameboard getGameboard() {
        return gameboard;
    }

    public Boolean getAttacked() {
        return attacked;
    }

    public Boolean getMoved() {
        return moved;
    }

    public int getMovesLeft() {
        return movesLeft;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public boolean didWin() {
        return win;
    }
    
}
