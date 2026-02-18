package model;

// An enemy with no special abilities
public class MWCultist extends Character {
    // EFFECTS: creates a Murky Water Cultist with basic stats
    public MWCultist() {
        this.setName("Murky Water Cultist");
        this.setMaxHealth(40);
        this.setHealth(40);
        this.setAttack(20);
        this.setAttacks(1);
        this.setRange(1);
        this.setSpeed(25);
        this.setMove(2);
        this.setAbility("A normal guy who has no special abilities");
        this.setAttackFilter(new AllyFilter());
    }

    // EFFECTS: prints out a description of mw cultist
    @Override
    public String toString() {
        return "Murky Water Cultist - A melee unit that is weak and doesn't do much";
    }
}
