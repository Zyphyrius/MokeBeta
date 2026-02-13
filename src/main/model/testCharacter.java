package model;

// A test character with inputtable stats and used for testing
public class testCharacter extends Character {

    // EFFECTS: creates a character with given stats
    public testCharacter(String name, int maxHealth, int attack, int range, int speed, int move, String ability, int xPos, int yPos, int attacks) {
        this.setName(name);
        this.setMaxHealth(maxHealth);
        this.setHealth(maxHealth);
        this.setAttack(attack);
        this.setRange(range);
        this.setSpeed(speed);
        this.setMove(move);
        this.setAbility(ability);
        this.setX(xPos);
        this.setY(yPos);
        this.setAttacks(attacks);
    }
}
