package model;

// A status effect that immobilizes the character, making them unable to move that isn't stackable
public class Immobilize implements StatusEffect {
    int duration;
    boolean positive;
    boolean stackable;

    // REQUIRES: duration > 0
    // EFFECTS: creates the negative effect immobilized with given duration
    public Immobilize(int duration) {
        this.duration = duration;
        positive = false;
        stackable = false;
    }

    // MODIFIES: character
    // EFFECTS: immobilizes the character, removing all their moves
    public void applyEffect(Character affected) {
        affected.setMovesLeft(0);
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
        return "immobilized";
    }
}
