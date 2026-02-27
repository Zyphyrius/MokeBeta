package model;

// A melee enemy that can consume moves left to attack
public class MWBerserker extends Character {
    // EFFECTS: creates a Murky Water Berserker with basic stats
    public MWBerserker() {
        this.setName("Murky Water Berserker");
        this.setMaxHealth(120);
        this.setHealth(120);
        this.setAttack(15);
        this.setAttacks(1);
        this.setRange(1);
        this.setSpeed(72);
        this.setMove(4);
        this.setAbility("Can consume moves left to perform additional attacks");
        this.setAttackFilter(new AllyFilter());
    }

    // EFFECTS: returns a new copy of this character.
    //          used for finding characters and making new instances of it
    public Character newCopy() {
        return new MWBerserker();
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

    // EFFECTS: prints out a description of mw berserker
    @Override
    public String toString() {
        return "Murky Water Berserker - A melee unit that consumes moves to do extra attacks";
    }
}
