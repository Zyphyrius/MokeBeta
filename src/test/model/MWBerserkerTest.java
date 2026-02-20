package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MWBerserkerTest {
    Character mwb;
    Character c1;

    @BeforeEach
    void runBefore() {
        mwb = new MWBerserker();
        c1 = new DummyCharacter("B", 100, 100, 1, 2, 1, "strong", 1, 1, 1);
        mwb.setMovesLeft(4);
        mwb.setAttacksLeft(1);
    }

    @Test
    void testAttack() {
        mwb.attack(c1);
        assertEquals(3, mwb.getMovesLeft());
        assertEquals(1, mwb.getAttacksLeft());
        mwb.attack(c1);
        mwb.attack(c1);
        mwb.attack(c1);
        assertEquals(0, mwb.getMovesLeft());
        assertEquals(1, mwb.getAttacksLeft());
        mwb.attack(c1);
        assertEquals(0, mwb.getAttacksLeft());
    }
    
    @Test
    void testToString() {
        assertEquals("Murky Water Berserker - A melee unit that consumes moves to do extra attacks", mwb.toString());
    }
}
