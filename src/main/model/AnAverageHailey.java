package model;

// An ally who is strengthens allies when attacking
public class AnAverageHailey extends Character {
    // EFFECTS: creates An Average Hailey with basic stats
    public AnAverageHailey() {
        this.setName("An Average Hailey");
        this.setMaxHealth(60);
        this.setHealth(60);
        this.setAttack(0);
        this.setAttacks(2);
        this.setRange(3);
        this.setSpeed(62);
        this.setMove(2);
        this.setAbility("Attacks twice.\nAttacks target allies and strengthen them by +20 atk for 2 turns");
        this.setAttackFilter(new AllyFilter());
    }

    // EFFECTS: returns a new copy of this character.
    //          used for finding characters and making new instances of it
    public Character newCopy() {
        return new AnAverageHailey();
    }

    // REQUIRES: attacksLeft > 0
    // MODIFIES: this
    // EFFECTS: performs strengthens target and removes 1 from attacksLeft
    public void attack(Character target) {
        target.addStatus(new Strengthen(2, 20));
        setAttacksLeft(getAttacksLeft() - 1);
    }

    // EFFECTS: prints out a description of an average hailey
    @Override
    public String toString() {
        return "An Average Hailey - A ranged unit that buffs allies on attack and can attack twice";
    }
}
