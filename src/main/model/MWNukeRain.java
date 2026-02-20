package model;

// An enemy with short range that can stun enemies hit
public class MWNukeRain extends Character {
    // EFFECTS: creates a Murky Water Nuke Rain with basic stats
    public MWNukeRain() {
        this.setName("Murky Water Nuke Rain");
        this.setMaxHealth(60);
        this.setHealth(60);
        this.setAttack(10);
        this.setAttacks(1);
        this.setRange(1);
        this.setSpeed(43);
        this.setMove(4);
        this.setAbility("Attacks apply stun for 1 turn");
        this.setAttackFilter(new AllyFilter());
    }

    // REQUIRES: attacksLeft > 0
    // MODIFIES: this
    // EFFECTS: performs attack on a target and removes 1 from attacksLeft. then stun target for 1 turn
    public void attack(Character target) {
        target.hurt(this.getAttack(), this);
        target.addStatus(new Stun(1));
        setAttacksLeft(getAttacksLeft() - 1);
    }

    // EFFECTS: prints out a description of mw nuke rain
    @Override
    public String toString() {
        return "Murky Water Nuke Rain - A low hp melee unit that stuns enemies hit";
    }
}
