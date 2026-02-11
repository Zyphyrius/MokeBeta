package model;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CharacterTest {
    Character c1;
    Character c2;
    Character c3;
    Character c4;

    @BeforeEach
    void runBefore() {
        c1 = new testCharacter("A", 100, 10, 2, 2, 2, "a dude", 0, 0);
        c2 = new testCharacter("B", 100, 100, 1, 2, 1, "strong", 0, 1);
    }

    @Test
    void testConstructor() {
        assertEquals("A", c1.getName());
        assertEquals(100, c1.getHealth());
        assertEquals(2, c1.getRange());
        assertEquals(2, c1.getSpeed());
        assertEquals(2, c1.getMove());
        assertEquals("a dude", c1.getAbility());
        assertEquals(0, c1.getX());
        assertEquals(0, c1.getY());
    }
}
