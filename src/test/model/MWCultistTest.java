package model;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MWCultistTest {
    Character mwc;

    @BeforeEach
    void runBefore() {
        mwc = new MWCultist();
    }

    @Test
    void testConstructor() {
        assertEquals("Murky Water Cultist", mwc.getName());
        assertEquals(40, mwc.getMaxHealth());
        assertEquals(40, mwc.getHealth());
        assertEquals(20, mwc.getAttack());
        assertEquals(1,  mwc.getAttacks());
        assertEquals(1, mwc.getRange());
        assertEquals(25, mwc.getSpeed());
        assertEquals(2, mwc.getMove());
        assertEquals("A normal guy who has no special abilities", mwc.getAbility());
    }

    @Test
    void testToString() {
        assertEquals("Murky Water Cultist - A melee unit that is weak and doesn't do much", mwc.toString());
    }

    @Test
    void testCopy() {
        assertEquals(mwc.getClass(), mwc.newCopy().getClass());
    }
}

