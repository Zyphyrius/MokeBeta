package model;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MopedMarauderTest {
    Character mm;
    Character c1;
    Character c2;
    MokeGame mg;

    @BeforeEach
    void runBefore() {
        mm = new MopedMarauder();
        c1 = new DummyCharacter("A", 10, 10, 2, 2, 2, "a dude", 2, 2, 1);
        c2 = new DummyCharacter("B", 100, 100, 1, 2, 1, "strong", 0, 3, 1);
        mm.setX(0);
        mm.setY(0);
        mg = new MokeGame(new ArrayList<Character>(List.of(mm)), new ArrayList<Character>(List.of(c1, c2)));
    }

    @Test
    void testMoveAttackReset() {
        mm.setMovesLeft(5);
        mm.moveRight();
        assertEquals(4, mm.getMovesLeft());
        mm.moveDown();
        assertEquals(5, mm.getMovesLeft());
        assertTrue(c1.isDead());
        mg.checkDead();
        mm.moveDown();
        mm.moveDown();
        mm.moveUp();
        mm.moveLeft();
        mm.moveRight();
        assertEquals(50, c2.getHealth());
        assertEquals(0, mm.getMovesLeft());
    }

    @Test
    void testAttackReset() {
        mm.setMovesLeft(0);
        mm.attack(c1);
        assertEquals(5, mm.getMovesLeft());
        mm.setMovesLeft(0);
        mm.attack(c2);
        assertEquals(0, mm.getMovesLeft());
    }

    @Test
    void testToString() {
        assertEquals("Moped Marauder - A ranged unit that attacks adjacent "
                + "enemies when moving and resets moves when killing", mm.toString());
    }
}
