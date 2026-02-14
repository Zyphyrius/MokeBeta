package model;

import java.util.ArrayList;

// an instance of the game that handles all characters and turn actions
public class MokeGame {
    private static ArrayList<Character> allies;
    private static ArrayList<Character> enemies;
    private static ArrayList<Character> turnOrder;
    private Character currentCharacter;
    private Gameboard gameboard;
    private int turnIndex;
    private boolean gameOver;
    private boolean win;
    
    // REQUIRES: allies.size() and enemies.size() > 0
    // EFFECTS: makes a game with allies and enemies
    //          forms a gameboard with dimensions allies.size() or enemies.size() + 3,
    //          depending which is larger.
    //          creates turn order for game based off speed, 
    //          then set current character to the first character in the turn order and reset its move/attacks left
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
        currentCharacter.setMovesLeft(currentCharacter.getMove());
        currentCharacter.setAttacksLeft(currentCharacter.getAttacks());
        gameOver = false;
    }

    // EFFECTS: returns the turn order based off all character's speed
    //          if tied, prioritize first character in list, prioritizing allies before enemies
    public ArrayList<Character> makeTurnOrder() {
        ArrayList<Character> newTurnOrder = new ArrayList<Character>();
        ArrayList<Character> allCharacters = new ArrayList<Character>(allies);
        allCharacters.addAll(enemies);
        for (Character characterToAdd : allCharacters) {
            int index = 0;
            for (Character characterToCompare : newTurnOrder) {
                if (characterToAdd.getSpeed() > characterToCompare.getSpeed()) {
                    break;
                } else {
                    index++;
                }
            }
            newTurnOrder.add(index, characterToAdd);
        }

        return newTurnOrder;
    }

    // MODIFIES: this
    // EFFECTS: sets current character to next character in turn order and resets move/attacks left
    //          when at end of turn order, return to first
    public void nextTurn() {
        turnIndex++;
        if (turnIndex >= turnOrder.size()) {
            turnIndex = 0;
        }
        currentCharacter = turnOrder.get(turnIndex);
        currentCharacter.setMovesLeft(currentCharacter.getMove());
        currentCharacter.setAttacksLeft(currentCharacter.getAttacks());
    }

    // MODIFIES: character
    // EFFECTS: initializes game by placing all enemies in the top left 
    //      and all allies in the bottom right and setting their x,y values
    public void startBoard() {
        for (Character enemy : enemies) {
            gameboard.placeCharacter(enemies.indexOf(enemy), 0, enemy);
            enemy.setX(enemies.indexOf(enemy));
            enemy.setY(0);
        }
        for (Character ally : allies) {
            gameboard.placeCharacter(gameboard.getRowLength() - 1 - allies.indexOf(ally),
                                     gameboard.getColumnLength() - 1, ally);
            ally.setX(gameboard.getRowLength() - 1 - allies.indexOf(ally));
            ally.setY(gameboard.getColumnLength() - 1);
        }
    }

    // MODIFIES: this
    // EFFECTS: removes all dead characters from allies, enemies, turn order, 
    //          and gameboard then change turnIndex accordingly, then checks if won/lost
    public void checkDead() {
        Character firstAlive = findFirstAlive();
        AliveFilter aliveFilter = new AliveFilter();
        allies = aliveFilter.characterFilter(allies);
        enemies = aliveFilter.characterFilter(enemies);
        gameboard.clearDeadCharacterTiles();
        checkGameOver();
        if (!gameOver) {
            turnOrder = makeTurnOrder();
            turnIndex = turnOrder.indexOf(firstAlive);
            currentCharacter = turnOrder.get(turnIndex);
        }    
    }

    // EFFECTS: returns first alive character, starting with the current character
    public Character findFirstAlive() {
        int tempTurnIndex = turnIndex;
        for (int i = 0; i < turnOrder.size(); i++) {
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
    // EFFECTS: gets current character to attack target
    public void attackCharacter(Character target) {
        currentCharacter.attack(target);
    }

    // EFFECTS: moves current character in direction if valid, then places on gameboard. true if valid, false if not
    public Boolean moveCharacter(String direction) {
        int newX = currentCharacter.getX();
        int newY = currentCharacter.getY();
        Runnable moveDirection = null;
        if (direction.toLowerCase().equals("up")) {
            newY -= 1;
            moveDirection = () -> currentCharacter.moveUp();
        } else if (direction.toLowerCase().equals("down")) {
            newY += 1;
            moveDirection = () -> currentCharacter.moveDown();
        } else if (direction.toLowerCase().equals("left")) {
            newX -= 1;
            moveDirection = () -> currentCharacter.moveLeft();
        } else if (direction.toLowerCase().equals("right")) {
            newX += 1;
            moveDirection = () -> currentCharacter.moveRight();
        }
        if (gameboard.validTile(newX, newY)) {
            moveDirection.run();
            gameboard.placeCharacter(newX, newY, currentCharacter);
            return true;
        }
        return false;
    }

    // EFFECTS: removes all from movesLeft
    public void endMove() {
        currentCharacter.setMovesLeft(0);
    }

    // MODIFIES: this
    // EFFECTS: set current character to character, and set corresponding turnIndex
    public void setCurrentCharacter(Character c) {
        currentCharacter = c;
        turnIndex = turnOrder.indexOf(c);
    }

    // EFFECTS: sets gameover to true and sets win/loss
    public void setGameOver(boolean win) {
        gameOver = true;
        this.win = win;
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

    public boolean isGameOver() {
        return gameOver;
    }

    public boolean didWin() {
        return win;
    }

    public boolean canAttack() {
        return currentCharacter.getAttacksLeft() > 0;
    }

    public boolean canMove() {
        return currentCharacter.getMovesLeft() > 0;
    }
    
}
