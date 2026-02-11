package model;

import java.util.ArrayList;

// Abstract class of a character, with all stats and standard functions
public abstract class Character {
    private String name;
    private int maxHealth;
    private int health;
    private int attack;
    private int range;
    private int speed;
    private int move;
    private String ability;
    private int xPos;
    private int yPos;
    //private ArrayList<String> states;

    // MODIFIES: this
    // EFFECTS: lose health equal to damage, if damage < 0, do nothing
    public void Hurt(int damage) {

    }

    // EFFECTS: return true if health <= 0
    public Boolean isDead() {
        return false;
    }

    // MODIFIES: target
    // EFFECTS: performs attack on a target
    public void attack(Character target) {

    }

    // MODIFIES: this, gameboard
    // EFFECTS: moves character up by 1 tile if valid
    public void moveUp() {

    }

    // MODIFIES: this, gameboard
    // EFFECTS: moves character down by 1 tile if valid
    public void moveDown() {

    }

    // MODIFIES: this, gameboard
    // EFFECTS: moves character left by 1 tile if valid
    public void moveLeft() {

    }

    // MODIFIES: this, gameboard
    // EFFECTS: moves character right by 1 tile if valid
    public void moveRight() {

    }

    // EFFECTS: returns name
    public String getName() {
        return name;
    }

    // MODIFIES: this 
    // EFFECTS: sets character's name
    public void setName(String name) {
        this.name = name;
    }

    // EFFECTS: returns health
    public int getHealth() {
        return health;
    }

    // MODIFIES: this 
    // EFFECTS: sets character's max health
    public void setMaxHealth(int maxHP) {
        this.maxHealth = maxHP;
    }

    // MODIFIES: this 
    // EFFECTS: sets character's health
    public void setHealth(int hp) {
        this.health = hp;
    }

    // EFFECTS: returns xPos
    public int getX() {
        return xPos;
    }

    // MODIFIES: this 
    // EFFECTS: sets character's x
    public void setX(int xPos) {
        this.xPos = xPos;
    }

    // EFFECTS: returns yPos
    public int getY() {
        return yPos;
    }

    // MODIFIES: this 
    // EFFECTS: sets character's y
    public void setY(int yPos) {
        this.yPos = yPos;
    }

    // EFFECTS: returns ability description
    public String getAbility() {
        return ability;
    }

    // MODIFIES: this 
    // EFFECTS: sets character's ability description
    public void setAbility(String ability) {
        this.ability = ability;
    }

    // EFFECTS: returns attack
    public int getAttack() {
        return attack;
    }

    // MODIFIES: this 
    // EFFECTS: sets character's attack
    public void setAttack(int attack) {
        this.attack = attack;
    }

    // EFFECTS: returns speed
    public int getSpeed() {
        return speed;
    }

    // MODIFIES: this 
    // EFFECTS: sets character's speed
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    // EFFECTS: returns move
    public int getMove() {
        return move;
    }

    // MODIFIES: this 
    // EFFECTS: sets character's move
    public void setMove(int move) {
        this.move = move;
    }

    // EFFECTS: returns range
    public int getRange() {
        return range;
    }

    // MODIFIES: this 
    // EFFECTS: sets character's range
    public void setRange(int range) {
        this.range = range;
    }
 }
