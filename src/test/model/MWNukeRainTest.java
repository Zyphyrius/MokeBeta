package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MWNukeRainTest {
    Character mwnr;
    Character c1;

    @BeforeEach
    void runBefore() {
        mwnr = new MWNukeRain();
        c1 = new DummyCharacter("B", 100, 100, 1, 2, 1, "strong", 1, 1, 1);
        mwnr.setMovesLeft(4);
        mwnr.setAttacksLeft(1);
    }

    @Test
    void testAttack() {
        mwnr.attack(c1);
        assertEquals(1, c1.getStatuses().size());
        assertEquals(1, c1.getStatuses().get(0).getDuration());
    }
    
    @Test
    void testToString() {
        assertEquals("Murky Water Nuke Rain - A low hp melee unit that stuns enemies hit", mwnr.toString());
    }

    @Test
    void testCopy() {
        assertEquals(mwnr.getClass(), mwnr.newCopy().getClass());
    }
}
