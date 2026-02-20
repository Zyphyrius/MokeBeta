package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

public class StrengthenTest {
    Character character;
    Strengthen strengthen;

    @BeforeEach
    void runBefore() {
        character = new DummyCharacter("guy", 100, 10, 1, 2, 3, "test guy", 0, 0, 2);
        strengthen = new Strengthen(2, 10);
    }

    @Test
    void testConstructor() {
        assertEquals(2, strengthen.getDuration());
        assertTrue(strengthen.isPositive());
        assertTrue(strengthen.isStackable());
        assertEquals("strengthened by 10 attack", strengthen.toString());
    }

    @Test
    void testApply() {
        strengthen.applyEffect(character);
        assertEquals(20, character.getAttack());
        strengthen.applyEffect(character);
        assertEquals(20, character.getAttack());
    }

    @Test
    void testTickDownNoApply() {
        assertEquals(2, strengthen.getDuration());
        strengthen.tickDown(character);
        assertEquals(1, strengthen.getDuration());
        strengthen.tickDown(character);
        assertEquals(0, strengthen.getDuration());
        assertEquals(10, character.getAttack());
    }

    @Test
    void testTickDownApply() {
        strengthen.applyEffect(character);
        assertEquals(2, strengthen.getDuration());
        strengthen.tickDown(character);
        assertEquals(1, strengthen.getDuration());
        strengthen.tickDown(character);
        assertEquals(0, strengthen.getDuration());
        assertEquals(10, character.getAttack());
    }
}
