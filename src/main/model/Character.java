package model;

import java.util.ArrayList;

// Abstract class of a character, with all stats and standard functions
public abstract class Character {
    private String name;
    private int maxHealth;
    private int health;
    private int attack;
    private int attacks;
    private int range;
    private int speed;
    private int move;
    private String ability;
    private int xPos;
    private int yPos;
    private int attacksLeft;
    private int movesLeft;
    //private ArrayList<String> states;

    // MODIFIES: this
    // EFFECTS: lose health equal to damage, if damage <= 0, do nothing
    //          if damage >= health, set health to 0
    public void hurt(int damage, Character attacker) {
        if (damage > 0) {
            if (damage >= health) {
                this.health = 0;
            } else {
                this.health -= damage;
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: increase health by healing, health can't exceed max health
    //          can't heal someone who is dead
    public void heal(int healing) {
        if (!isDead()) {
            this.health += healing;
            if (health > maxHealth) {
                health = maxHealth;
            }
        }
    }

    // EFFECTS: return true if health <= 0
    public Boolean isDead() {
        if (health > 0) {
            return false;
        } else {
            return true;
        }
    }

    // MODIFIES: this, target, attacksLeft > 0
    // EFFECTS: performs attack on a target and removes 1 from attacksLeft
    public void attack(Character target) {
        target.hurt(attack, this);
        attacksLeft -= 1;
    }

    // EFFECTS: returns all characters in range that are not itself with specified filter
    public ArrayList<Character> getInRange(CharacterFilter cf) {
        ArrayList<Character> inRange = new ArrayList<Character>();
        ArrayList<Character> allCharacters = new ArrayList<Character>(MokeGame.getAllies());
        allCharacters.addAll(MokeGame.getEnemies());
        allCharacters = cf.characterFilter(allCharacters);
        for (Character c : allCharacters) {
            if (c != this & range >= Math.abs(c.getX() - this.xPos) & range >= Math.abs(c.getY() - this.yPos)) {
                inRange.add(c);
            }
        }
        return inRange;
    }

    // MODIFIES: this, movesLeft > 0
    // EFFECTS: moves character position coordinates up by 1
    //          and removes 1 from movesLeft
    public void moveUp() {
        this.yPos -= 1;
        this.movesLeft -= 1;
    }

    // MODIFIES: this, movesLeft > 0
    // EFFECTS: moves character position coordinates down by 1
    //          and removes 1 from movesLeft
    public void moveDown() {
        this.yPos += 1;
        this.movesLeft -= 1;
    }

    // MODIFIES: this, movesLeft > 0
    // EFFECTS: moves character position coordinates right by 1
    //          and removes 1 from movesLeft
    public void moveRight() {
        this.xPos += 1;
        this.movesLeft -= 1;
    }

    // MODIFIES: this, movesLeft > 0
    // EFFECTS: moves character position coordinates left by 1
    //          and removes 1 from movesLeft
    public void moveLeft() {
        this.xPos -= 1;
        this.movesLeft -= 1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHealth() {
        return health;
    }

    public void setMaxHealth(int maxHP) {
        this.maxHealth = maxHP;
    }

    public void setHealth(int hp) {
        this.health = hp;
    }

    public int getX() {
        return xPos;
    }

    public void setX(int xPos) {
        this.xPos = xPos;
    }

    public int getY() {
        return yPos;
    }

    public void setY(int yPos) {
        this.yPos = yPos;
    }

    public String getAbility() {
        return ability;
    }

    public void setAbility(String ability) {
        this.ability = ability;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getAttacks() {
        return attacks;
    }
    
    public void setAttacks(int attacks) {
        this.attacks = attacks;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getMove() {
        return move;
    }

    public void setMove(int move) {
        this.move = move;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public int getAttacksLeft() {
        return attacksLeft;
    }

    public void setAttacksLeft(int attacksLeft) {
        this.attacksLeft = attacksLeft;
    }

    public int getMovesLeft() {
        return movesLeft;
    }

    public void setMovesLeft(int movesLeft) {
        this.movesLeft = movesLeft;
    }
 }
