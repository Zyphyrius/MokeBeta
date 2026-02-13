package model;

// A test character with inputtable stats and used for testing
public class DummyCharacter extends Character {

    // EFFECTS: creates a character with given stats and default no attack filter
    public DummyCharacter(String name, int maxHealth, int attack, int range, int speed, int move, 
                        String ability, int cordX, int cordY, int attacks) {
        this.setName(name);
        this.setMaxHealth(maxHealth);
        this.setHealth(maxHealth);
        this.setAttack(attack);
        this.setRange(range);
        this.setSpeed(speed);
        this.setMove(move);
        this.setAbility(ability);
        this.setX(cordY);
        this.setY(cordY);
        this.setAttacks(attacks);
    }
}
