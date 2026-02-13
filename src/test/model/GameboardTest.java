package model;


import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GameboardTest {
    Character c1;
    Character c2;
    Gameboard g;

    @BeforeEach
    void runBefore() {
        c1 = new testCharacter("A", 100, 10, 2, 2, 2, "a dude", 0, 0, 1);
        c2 = new testCharacter("B", 100, 100, 1, 2, 1, "strong", 0, 0, 1);
        g = new Gameboard(5, 5);
    }

    @Test
    void testConstructor() {
        assertEquals(25, g.getTiles().size());
    }

    @Test
    void testPlaceCharacter() {
        g.placeCharacter(0, 0, c1);
        assertEquals(c1, g.getTiles().get(0).getCharacter());
        g.placeCharacter(1, 2, c1);
        assertEquals(null, g.getTiles().get(0).getCharacter());
        assertEquals(c1, g.getTiles().get(11).getCharacter());
        g.placeCharacter(1, 1, c2);
        assertEquals(c2, g.getTiles().get(6).getCharacter());
        assertEquals(c1, g.getTiles().get(11).getCharacter());
    }

    @Test
    void testFindTile() {
        assertEquals(g.getTiles().get(0), g.findTile(0, 0));
        assertEquals(g.getTiles().get(24), g.findTile(4, 4));
    }

    @Test
    void testValidTile() {
        assertTrue(g.validTile(0, 0));
        g.placeCharacter(0, 0, c1);
        assertFalse(g.validTile(0, 0));
        assertTrue(g.validTile(4, 0));
        assertFalse(g.validTile(5, 0));
        assertFalse(g.validTile(0, 9));
        assertFalse(g.validTile(6, 9));
        assertFalse(g.validTile(-5, -2));
    }

    @Test
    void testClearDeadCharacterTiles() {
        g.placeCharacter(0, 0, c1);
        g.placeCharacter(1, 2, c2);
        g.clearDeadCharacterTiles();
        assertEquals(c1, g.getTiles().get(0).getCharacter());
        assertEquals(c2, g.getTiles().get(11).getCharacter());
        c1.hurt(1000, c1);
        g.clearDeadCharacterTiles();
        assertEquals(null, g.getTiles().get(0).getCharacter());
        assertEquals(c2, g.getTiles().get(11).getCharacter());
    }
}
