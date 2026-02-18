package model;

import java.util.ArrayList;

// Moped Marauder is an ally character that can do damage by moving and resets moves when killing
public class MopedMarauder extends Character {
    // EFFECTS: creates a Moped Marauder with basic stats
    public MopedMarauder() {
        this.setName("Moped Marauder");
        this.setMaxHealth(70);
        this.setHealth(70);
        this.setAttack(30);
        this.setAttacks(1);
        this.setRange(2);
        this.setSpeed(23);
        this.setMove(5);
        this.setAbility("After each move, all enemies in range 1 take 10 dmg\n"
                + "When killing an enemy, reset moves to max");
        this.setAttackFilter(new EnemyFilter());
    }

    // REQUIRES: movesLeft > 0
    // MODIFIES: this
    // EFFECTS: moves character position coordinates up by 1
    //          and removes 1 from movesLeft
    //          then attacks adjacent enemies
    public void moveUp() {
        setY(getY() - 1);
        setMovesLeft(getMovesLeft() - 1);
        attackOnMove();
    }

    // REQUIRES: movesLeft > 0
    // MODIFIES: this
    // EFFECTS: moves character position coordinates down by 1
    //          and removes 1 from movesLeft
    //          then attacks adjacent enemies
    public void moveDown() {
        setY(getY() + 1);
        setMovesLeft(getMovesLeft() - 1);
        attackOnMove();
    }

    // REQUIRES: movesLeft > 0
    // MODIFIES: this
    // EFFECTS: moves character position coordinates right by 1
    //          and removes 1 from movesLeft
    //          then attacks adjacent enemies
    public void moveRight() {
        setX(getX() + 1);
        setMovesLeft(getMovesLeft() - 1);
        attackOnMove();
    }

    // REQUIRES: movesLeft > 0
    // MODIFIES: this
    // EFFECTS: moves character position coordinates left by 1
    //          and removes 1 from movesLeft
    //          then attacks adjacent enemies
    public void moveLeft() {
        setX(getX() - 1);
        setMovesLeft(getMovesLeft() - 1);
        attackOnMove();
    }

    // EFFECTS: attacks all adjacent enemies for 10 dmg when moving
    //          killing an enemy resets moves
    private void attackOnMove() {
        ArrayList<Character> inRange = getInRange(getAttackFilter(), 1);
        for (Character target : inRange) {
            target.hurt(10, this);
            if (target.isDead()) {
                setMovesLeft(getMove());
            }
        }
    }

    // REQUIRES: attacksLeft > 0
    // MODIFIES: this
    // EFFECTS: performs attack on a target and removes 1 from attacksLeft
    //          if enemy dies, reset moves
    public void attack(Character target) {
        target.hurt(this.getAttack(), this);
        setAttacksLeft(getAttacksLeft() - 1);
        if (target.isDead()) {
            setMovesLeft(getMove());
        }
    }

    // EFFECTS: prints out a description of moped marauder
    @Override
    public String toString() {
        return "Moped Marauder - A ranged unit that attacks adjacent enemies when moving and resets moves when killing";
    }
}
