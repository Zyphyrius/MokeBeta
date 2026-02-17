package model;

import java.util.ArrayList;

// A class that handles all enemy turn actions. 
// If neededForGame = false, then the game is player vs player and AI is not needed
public class EnemyAI {
    MokeGame game;
    boolean neededForGame;
    boolean attacked;
    boolean moved;

    // EFFECTS: creates an enemy ai for the game with attacked and moved = false, and whether it's needed or not.
    public EnemyAI(MokeGame game, boolean needed) {
        this.game = game;
        neededForGame = needed;
        attacked = false;
        moved = false;
    }

    // EFFECTS: returns the next turn action for the enemy.
    //          every enemy turn goes: move, attack, end
    //          checks boolean and attacks/moves left in case enemy has 0 moves/attacks in stats
    public int determineAction(Character enemy) {
        if (!moved & enemy.getMove() != 0) {
            return 2;
        } else if (!attacked & enemy.getAttacks() != 0) {
            return 1;
        } else {
            return 4;
        }
    }

    // EFFECTS: returns the next move for the enemy.
    //          moves in direction towards nearest ally if not already in range.
    //          moves vertically before horizontally if tied for distance
    //          if in range or no valid spots that move towards nearest ally, don't move.
    //          when moves left = 0 or choose not to move, moved = true
    //          will always try to move before attacking
    public int determineMove(Character enemy) {
        if (enemy.getInRange(new AllyFilter()).isEmpty()) {
            Character closest = getNearestAlly(enemy);
            boolean vertGreater = getYDistance(enemy, closest) >= getXDistance(enemy, closest);
            if (shouldMoveUp(vertGreater, enemy.getX(), enemy.getY(), closest.getX(), closest.getY())) {
                checkLastMove(enemy);
                return 1;
            } else if (shouldMoveDown(vertGreater, enemy.getX(), enemy.getY(), closest.getX(), closest.getY())) {
                checkLastMove(enemy);
                return 2;
            } else if (shouldMoveLeft(enemy.getX(), enemy.getY(), closest.getX())) {
                checkLastMove(enemy);
                return 3;
            } else if (shouldMoveRight(enemy.getX(), enemy.getY(), closest.getX())) {
                checkLastMove(enemy);
                return 4;
            }
        }
        moved = true;
        return 0;
    }

    // EFFECTS: returns true if either (vertical distance >= horizontal distance OR
    //          preferred horizontal movement is unperformable)
    //          AND enemy Y > closest Y AND tile above is valid
    private boolean shouldMoveUp(boolean vertGreater, int enemyX, int enemyY, int closestX, int closestY) {
        Gameboard board = game.getGameboard();
        return (vertGreater
            || ((enemyX > closestX & !board.validTile(enemyX - 1, enemyY))
                || (enemyX < closestX & !board.validTile(enemyX + 1, enemyY))))
                & enemyY > closestY & board.validTile(enemyX, enemyY - 1);
    }

    // EFFECTS: returns true if either (vertical distance >= horizontal distance OR
    //          preferred horizontal movement is unperformable)
    //          AND enemy Y < closest Y AND tile below is valid
    private boolean shouldMoveDown(boolean vertGreater, int enemyX, int enemyY, int closestX, int closestY) {
        Gameboard board = game.getGameboard();
        return (vertGreater
            || ((enemyX > closestX & !board.validTile(enemyX - 1, enemyY))
                || (enemyX < closestX & !board.validTile(enemyX + 1, enemyY))))
                & enemyY < closestY & board.validTile(enemyX, enemyY + 1);
    }

    // EFFECTS: returns true if enemy X > closest X AND tile left is valid
    private boolean shouldMoveLeft(int enemyX, int enemyY, int closestX) {
        Gameboard board = game.getGameboard();
        return enemyX > closestX & board.validTile(enemyX - 1, enemyY);
    }

    // EFFECTS: returns true if enemy X < closest X AND tile right is valid
    private boolean shouldMoveRight(int enemyX, int enemyY, int closestX) {
        Gameboard board = game.getGameboard();
        return enemyX < closestX & board.validTile(enemyX + 1, enemyY);
    }


    // MODIFIES: this
    // EFFECTS: sets moved to true if this is the last move for the turn
    private void checkLastMove(Character enemy) {
        if (enemy.getMovesLeft() == 1) {
            moved = true;
        }
    }

    // MODIFIES: this
    // EFFECTS: sets attacked to true if this is the last attack for the turn
    //          IF enemy is berserker, moves left must also be 0
    private void checkLastAttack(Character enemy) {
        if (enemy.getAttacksLeft() == 1) {
            if (!(enemy instanceof MWBerserker) || enemy.getMovesLeft() == 0) {
                attacked = true;
            }
        }
    }

    // EFFECTS: returns the next attack for the enemy
    //          attacks whoever is lowest hp within range
    //          when out of attacks or no more targets in range, attacked = true
    public int determineAttack(Character enemy) {
        ArrayList<Character> inRange = enemy.getInRange(enemy.getAttackFilter());
        if (!inRange.isEmpty()) {
            Character lowest = inRange.get(0);
            for (Character c : inRange) {
                if (lowest.getHealth() > c.getHealth()) {
                    lowest = c;
                }
            }
            checkLastAttack(enemy);
            return inRange.indexOf(lowest) + 1;
        }
        attacked = true;
        return 0;
    }

    // REQUIRES: allies size > 0
    // EFFECTS: returns the closest ally to the given enemy
    private Character getNearestAlly(Character enemy) {
        Character closest = MokeGame.getAllies().get(0);
        for (Character ally : MokeGame.getAllies()) {
            if (getXDistance(ally, enemy) + getYDistance(ally, enemy)  
                    < getXDistance(enemy, closest) + getYDistance(enemy, closest)) {
                closest = ally;
            }
        }
        return closest;
    }

    // EFFECTS: returns distance from c1 to c2 on x-axis
    private int getXDistance(Character c1, Character c2) {
        return Math.abs(c1.getX() - c2.getX());
    }

    // EFFECTS: returns distance from c1 to c2 on y-axis
    private int getYDistance(Character c1, Character c2) {
        return Math.abs(c1.getY() - c2.getY());
    }

    public boolean getNeededForGame() {
        return neededForGame;
    }

    public void setNeededForGame(boolean needed) {
        neededForGame = needed;
    }

    public boolean getAttacked() {
        return attacked;
    }

    public void setAttacked(boolean attacked) {
        this.attacked = attacked;
    }

    public boolean getMoved() {
        return moved;
    }

    public void setMoved(boolean moved) {
        this.moved = moved;
    }
}
