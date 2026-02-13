package model;

// Lord Fishbowl is an ally character that can't be hurt by far away enemies
public class LordFishbowl extends Character{
    public LordFishbowl() {
        this.setName("Lord Fishbowl");
        this.setMaxHealth(100);
        this.setHealth(100);
        this.setAttack(30);
        this.setAttacks(2);
        this.setRange(2);
        this.setSpeed(40);
        this.setMove(2);
        this.setAbility("Can attack twice. \n\tCan't be hurt by enemies outside his range");
    }

    // MODIFIES: this
    // EFFECTS: lose health equal to damage, if damage <= 0 or enemy not in range, do nothing
    //          if damage >= health, set health to 0
    public void hurt(int damage, Character attacker) {
        if (damage > 0 & this.getInRange(new EnemyFilter()).contains(attacker)) {
            if (damage >= this.getHealth()) {
                this.setHealth(0);
            } else {
                this.setHealth(this.getHealth() - damage);
            }
        }
    }


}
