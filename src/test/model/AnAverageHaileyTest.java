package model;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

public class AnAverageHaileyTest {
    Character aah;
    Character c1;

    @BeforeEach
    void runBefore() {
        aah = new AnAverageHailey();
        c1 = new DummyCharacter("A", 100, 10, 2, 2, 2, "nothing", 0, 0, 1);
    }

    @Test
    void testAttack() {
        aah.attack(c1);
        assertEquals(1, c1.getStatuses().size());
        aah.attack(c1);
        assertEquals(2, c1.getStatuses().size());
        c1.applyStatuses();
        assertEquals(50, c1.getAttack());
    }

    @Test
    void testToString() {
        assertEquals("An Average Hailey - A ranged unit that buffs allies on attack and can attack twice", aah.toString());
    }
}
