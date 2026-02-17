package model;

// An ally who is heals the lowest hp ally when this kills an enemy
public class BarcelonaBeefBogger extends Character {
    public BarcelonaBeefBogger() {
        this.setName("Barcelona Beef Bogger");
        this.setMaxHealth(90);
        this.setHealth(90);
        this.setAttack(40);
        this.setAttacks(1);
        this.setRange(2);
        this.setSpeed(22);
        this.setMove(4);
        this.setAbility("When killing an enemy, heal the lowest hp % ally for 50% of their max hp");
        this.setAttackFilter(new EnemyFilter());
    }

    // REQUIRES: attacksLeft > 0
    // MODIFIES: this
    // EFFECTS: performs attack on a target and removes 1 from attacksLeft
    //          if the target dies, find ally with lowest hp % and heal for 50% of their max hp
    public void attack(Character target) {
        target.hurt(this.getAttack(), this);
        setAttacksLeft(getAttacksLeft() - 1);
        if (target.isDead()) {
            Character lowest = MokeGame.getAllies().get(0);
            for (Character c : MokeGame.getAllies()) {
                double lowestPercentHP = (double) lowest.getHealth() / lowest.getMaxHealth();
                double currentPercentHP = (double) c.getHealth() / c.getMaxHealth();
                if (currentPercentHP < lowestPercentHP) {
                    lowest = c;
                }
            }
            int healing = (int) (lowest.getMaxHealth() * 0.5);
            lowest.heal(healing);
        }
    }
}
