package model;

// An ally character that can heal with his attack
public class HotMould extends Character {
    public HotMould() {
        this.setName("Hot Mould");
        this.setMaxHealth(110);
        this.setHealth(110);
        this.setAttack(30);
        this.setAttacks(1);
        this.setRange(4);
        this.setSpeed(80);
        this.setMove(3);
        this.setAbility("Can heal allies or damage enemies");
        this.setAttackFilter(new NoFilter());
    }

    // REQUIRES: attacksLeft > 0
    // MODIFIES: this
    // EFFECTS: performs attack on a target and removes 1 from attacksLeft.
    //          if ally, heal. if enemy, hurt
    public void attack(Character target) {
        if (MokeGame.getAllies().contains(target)) {
            target.heal(this.getAttack());
        } else {
            target.hurt(this.getAttack(), this);
        }
        setAttacksLeft(getAttacksLeft() - 1);
    }

}
