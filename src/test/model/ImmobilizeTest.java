package model;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.BeforeEach;

public class ImmobilizeTest {
    Character character;
    Immobilize immobilize;

    @BeforeEach
    void runBefore() {
        character = new DummyCharacter("guy", 100, 10, 1, 2, 3, "test guy", 0, 0, 2);
        immobilize = new Immobilize(2);
    }

    @Test
    void testConstructor() {
        assertEquals(2, immobilize.getDuration());
        assertFalse(immobilize.isPositive());
        assertEquals("immobilized", immobilize.toString());
        assertFalse(immobilize.isStackable());
    }

    @Test
    void testApply() {
        character.setMovesLeft(3);
        character.setAttacksLeft(2);
        immobilize.applyEffect(character);
        assertEquals(0, character.getMovesLeft());
        assertEquals(2, character.getAttacksLeft());
    }

    @Test
    void testTickDown() {
        assertEquals(2, immobilize.getDuration());
        immobilize.tickDown(character);
        assertEquals(1, immobilize.getDuration());
        immobilize.tickDown(character);
        assertEquals(0, immobilize.getDuration());
    }
}
