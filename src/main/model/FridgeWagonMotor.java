package model;

// An ally who is tanky, speedy and does more damage at lower health
public class FridgeWagonMotor extends Character {
    public FridgeWagonMotor() {
        this.setName("Fridge Wagon Motor");
        this.setMaxHealth(200);
        this.setHealth(200);
        this.setAttack(25);
        this.setAttacks(1);
        this.setRange(1);
        this.setSpeed(75);
        this.setMove(7);
        this.setAbility("Deal double damage when under 50% health");
        this.setAttackFilter(new EnemyFilter());
    }

    // REQUIRES: attacksLeft > 0
    // MODIFIES: this
    // EFFECTS: performs attack on a target and removes 1 from attacksLeft
    //          if under 50% health, deal double damage
    public void attack(Character target) {
        if (getHealth() <= getMaxHealth() * 0.5) {
            target.hurt(this.getAttack() * 2, this);
        } else {
            target.hurt(this.getAttack(), this);
        }
        setAttacksLeft(getAttacksLeft() - 1);
    }
}
