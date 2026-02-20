package model;

// A status effect that stuns the character, removing their attacks and moves
public class Stun implements StatusEffect {
    int duration;
    boolean positive;
    boolean stackable;

    // REQUIRES: duration > 0
    // EFFECTS: creates the negative effect stunned with given duration that isn't stackable
    public Stun(int duration) {
        this.duration = duration;
        positive = false;
        stackable = false;
    }

    // MODIFIES: character
    // EFFECTS: stuns the character, removing all their attacks and moves
    public void applyEffect(Character affected) {
        affected.setMovesLeft(0);
        affected.setAttacksLeft(0);
    }

    // MODIFIES: this
    // EFFECTS: reduces duration by 1
    public void tickDown(Character affected) {
        duration -= 1;
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
        return "stunned";
    }
}
