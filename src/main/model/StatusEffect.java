package model;

// An abstract class representing a status effect on a character
public interface StatusEffect {
    // MODIFIES: Character
    // EFFECTS: performs the effect's ability on the affected character
    void applyEffect(Character affected);

    // MODIFIES: this
    // EFFECTS: reduces duration by 1
    void tickDown(Character affected);

    int getDuration();

    boolean isPositive();

    boolean isStackable();

    String toString();
}
