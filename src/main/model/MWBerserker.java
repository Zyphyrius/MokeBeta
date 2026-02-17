package model;

// A melee enemy that can consume moves left to attack
public class MWBerserker extends Character {
    public MWBerserker() {
        this.setName("Murky Water Berserker");
        this.setMaxHealth(120);
        this.setHealth(120);
        this.setAttack(20);
        this.setAttacks(1);
        this.setRange(1);
        this.setSpeed(72);
        this.setMove(4);
        this.setAbility("Can consume moves left to perform additional attacks");
        this.setAttackFilter(new AllyFilter());
    }

    // REQUIRES: attacksLeft > 0
    // MODIFIES: this
    // EFFECTS: performs attack on a target and removes 1 from movesLeft, then from attacksLeft if no more moves
    public void attack(Character target) {
        target.hurt(this.getAttack(), this);
        if (this.getMovesLeft() < 1) {
            setAttacksLeft(getAttacksLeft() - 1);
        } else {
            setMovesLeft(getMovesLeft() - 1);
        }
    }
}
