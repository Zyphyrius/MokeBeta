package model;

import org.json.JSONObject;

// A status effect that buffs the character, increasing their atk by an amount
public class Strengthen implements StatusEffect {
    int duration;
    boolean positive;
    boolean stackable;
    int attack;
    boolean applied;

    // REQUIRES: duration > 0
    // EFFECTS: creates the positive effect strengthen with given duration and attack that's stackable
    //          sets applied to false
    public Strengthen(int duration, int attack) {
        this.duration = duration;
        positive = true;
        stackable = true;
        this.attack = attack;
        applied = false;
    }

    // MODIFIES: character
    // EFFECTS: buffs the character, giving it extra attack once if not applied yet
    //          sets applied to true
    public void applyEffect(Character affected) {
        if (!applied) {
            affected.setAttack(affected.getAttack() + attack);
            applied = true;
        }
    }

    // MODIFIES: this
    // EFFECTS: reduces duration by 1. if duration <= 0 and effect has been applied remove attack buff
    public void tickDown(Character affected) {
        duration -= 1;
        if (duration <= 0 & applied) {
            affected.setAttack(affected.getAttack() - attack);
        }
    }

    public int getDuration() {
        return duration;
    }

    public boolean isPositive() {
        return positive;
    }

    public boolean isStackable() {
        return stackable;
    }

    @Override
    public String toString() {
        return "strengthened by " + Integer.toString(attack) + " attack";
    }

    // EFFECTS: returns as a json object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", "strengthen");
        json.put("duration", duration);
        json.put("attack", attack);
        return json;
    }
}