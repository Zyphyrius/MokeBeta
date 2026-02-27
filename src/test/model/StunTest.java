package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.BeforeEach;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;

@ExcludeFromJacocoGeneratedReport
public class StunTest {
    Character character;
    Stun stun;

    @BeforeEach
    void runBefore() {
        character = new DummyCharacter("guy", 100, 10, 1, 2, 3, "test guy", 0, 0, 2);
        stun = new Stun(2);
    }

    @Test
    void testConstructor() {
        assertEquals(2, stun.getDuration());
        assertFalse(stun.isPositive());
        assertEquals("stunned", stun.toString());
        assertFalse(stun.isStackable());
    }

    @Test
    void testApply() {
        character.setMovesLeft(3);
        character.setAttacksLeft(2);
        stun.applyEffect(character);
        assertEquals(0, character.getMovesLeft());
        assertEquals(0, character.getAttacksLeft());
    }

    @Test
    void testTickDown() {
        assertEquals(2, stun.getDuration());
        stun.tickDown(character);
        assertEquals(1, stun.getDuration());
        stun.tickDown(character);
        assertEquals(0, stun.getDuration());
    }
}
