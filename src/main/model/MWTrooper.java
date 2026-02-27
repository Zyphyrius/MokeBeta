package model;

// An enemy with long range, but less damage upfront
public class MWTrooper extends Character {
    // EFFECTS: creates a Murky Water Trooper with basic stats
    public MWTrooper() {
        this.setName("Murky Water Trooper");
        this.setMaxHealth(90);
        this.setHealth(90);
        this.setAttack(30);
        this.setAttacks(1);
        this.setRange(3);
        this.setSpeed(32);
        this.setMove(3);
        this.setAbility("Does -10 damage when within range 1");
        this.setAttackFilter(new AllyFilter());
    }

    // EFFECTS: returns a new copy of this character.
    //          used for finding characters and making new instances of it
    public Character newCopy() {
        return new MWTrooper();
    }

    // REQUIRES: attacksLeft > 0
    // MODIFIES: this
    // EFFECTS: performs attack on a target and removes 1 from attacksLeft. if within range 1, do -10 damage
    public void attack(Character target) {
        if (Math.abs(target.getX() - this.getX()) <= 1 & Math.abs(target.getY() - this.getY()) <= 1) {
            target.hurt(this.getAttack() - 10, this);
        } else {
            target.hurt(this.getAttack(), this);
        }
        setAttacksLeft(getAttacksLeft() - 1);
    }

    // EFFECTS: prints out a description of mw trooper
    @Override
    public String toString() {
        return "Murky Water Trooper - A ranged unit that deals less damage up close";
    }
}
