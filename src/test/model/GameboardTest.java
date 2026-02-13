package model;


import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GameboardTest {
    Character c1;
    Character c2;
    Gameboard gb;

    @BeforeEach
    void runBefore() {
        c1 = new DummyCharacter("A", 100, 10, 2, 2, 2, "a dude", 0, 0, 1);
        c2 = new DummyCharacter("B", 100, 100, 1, 2, 1, "strong", 0, 0, 1);
        gb = new Gameboard(5, 5);
    }

    @Test
    void testConstructor() {
        assertEquals(25, gb.getTiles().size());
    }

    @Test
    void testPlaceCharacter() {
        gb.placeCharacter(0, 0, c1);
        assertEquals(c1, gb.getTiles().get(0).getCharacter());
        gb.placeCharacter(1, 2, c1);
        assertEquals(null, gb.getTiles().get(0).getCharacter());
        assertEquals(c1, gb.getTiles().get(11).getCharacter());
        gb.placeCharacter(1, 1, c2);
        assertEquals(c2, gb.getTiles().get(6).getCharacter());
        assertEquals(c1, gb.getTiles().get(11).getCharacter());
    }

    @Test
    void testFindTile() {
        assertEquals(gb.getTiles().get(0), gb.findTile(0, 0));
        assertEquals(gb.getTiles().get(24), gb.findTile(4, 4));
    }

    @Test
    void testValidTile() {
        assertTrue(gb.validTile(0, 0));
        gb.placeCharacter(0, 0, c1);
        assertFalse(gb.validTile(0, 0));
        assertTrue(gb.validTile(4, 0));
        assertFalse(gb.validTile(5, 0));
        assertFalse(gb.validTile(0, 9));
        assertFalse(gb.validTile(6, 9));
        assertFalse(gb.validTile(-5, -2));
    }

    @Test
    void testClearDeadCharacterTiles() {
        gb.placeCharacter(0, 0, c1);
        gb.placeCharacter(1, 2, c2);
        gb.clearDeadCharacterTiles();
        assertEquals(c1, gb.getTiles().get(0).getCharacter());
        assertEquals(c2, gb.getTiles().get(11).getCharacter());
        c1.hurt(1000, c1);
        gb.clearDeadCharacterTiles();
        assertEquals(null, gb.getTiles().get(0).getCharacter());
        assertEquals(c2, gb.getTiles().get(11).getCharacter());
    }
}
