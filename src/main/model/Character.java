package model;

import java.util.ArrayList;

// Abstract class of a character, with all stats and standard version of game functions and no statuses
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
    private int cordX;
    private int cordY;
    private int attacksLeft;
    private int movesLeft;
    private CharacterFilter charFilter;
    private ArrayList<StatusEffect> statuses = new ArrayList<StatusEffect>();

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

    // REQUIRES: healing >= 0
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

    // REQUIRES: attacksLeft > 0
    // MODIFIES: this
    // EFFECTS: performs attack on a target and removes 1 from attacksLeft
    public void attack(Character target) {
        target.hurt(attack, this);
        attacksLeft -= 1;
    }

    // EFFECTS: returns all characters in range that are not itself with specified filter
    public ArrayList<Character> getInRange(CharacterFilter cf, int range) {
        ArrayList<Character> inRange = new ArrayList<Character>();
        ArrayList<Character> allCharacters = new ArrayList<Character>(MokeGame.getAllies());
        allCharacters.addAll(MokeGame.getEnemies());
        allCharacters = cf.characterFilter(allCharacters);
        for (Character c : allCharacters) {
            if (c != this & range >= Math.abs(c.getX() - this.cordX) & range >= Math.abs(c.getY() - this.cordY)) {
                inRange.add(c);
            }
        }
        return inRange;
    }

    // REQUIRES: movesLeft > 0
    // MODIFIES: this
    // EFFECTS: moves character position coordinates up by 1
    //          and removes 1 from movesLeft
    public void moveUp() {
        this.cordY -= 1;
        this.movesLeft -= 1;
    }

    // REQUIRES: movesLeft > 0
    // MODIFIES: this
    // EFFECTS: moves character position coordinates down by 1
    //          and removes 1 from movesLeft
    public void moveDown() {
        this.cordY += 1;
        this.movesLeft -= 1;
    }

    // REQUIRES: movesLeft > 0
    // MODIFIES: this
    // EFFECTS: moves character position coordinates right by 1
    //          and removes 1 from movesLeft
    public void moveRight() {
        this.cordX += 1;
        this.movesLeft -= 1;
    }

    // REQUIRES: movesLeft > 0
    // MODIFIES: this
    // EFFECTS: moves character position coordinates left by 1
    //          and removes 1 from movesLeft
    public void moveLeft() {
        this.cordX -= 1;
        this.movesLeft -= 1;
    }

    // MODIFIES: this
    // EFFECTS: ticks down all effects, then removes ones with no more duration
    public void tickDownAll() {
        ArrayList<Integer> toRemove = new ArrayList<>();
        for (StatusEffect s : statuses) {
            s.tickDown(this);
            if (s.getDuration() <= 0) {
                toRemove.add(statuses.indexOf(s));
            }
        }
        for (int i : toRemove) {
            statuses.remove(i);
        }
    }

    // MODIFIES: this
    // EFFECTS: adds the status to statuses. 
    //           if not stackable and if already has the status, keep the one with longer duration
    public void addStatus(StatusEffect status) {
        boolean shouldAdd = true;
        int toRemove = -1;
        for (StatusEffect s : statuses) {
            if (s.getClass() == status.getClass() & !s.isStackable()) {
                if (s.getDuration() < status.getDuration()) {
                    toRemove = statuses.indexOf(s);
                } else {
                    shouldAdd = false;
                }
            }
        }
        if (toRemove != -1) {
            statuses.remove(toRemove);
        }
        if (shouldAdd) {
            statuses.add(status);
        }
    }

    // EFFECTS: applies all statuses on the character
    public void applyStatuses() {
        for (StatusEffect s : statuses) {
            s.applyEffect(this);
        }
    }

    public ArrayList<StatusEffect> getStatuses() {
        return statuses;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMaxHealth() {
        return maxHealth;
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
        return cordX;
    }

    public void setX(int cordX) {
        this.cordX = cordX;
    }

    public int getY() {
        return cordY;
    }

    public void setY(int cordY) {
        this.cordY = cordY;
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

    public void setAttackFilter(CharacterFilter cf) {
        charFilter = cf;
    }

    public CharacterFilter getAttackFilter() {
        return charFilter;
    }
}
