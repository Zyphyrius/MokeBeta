package model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MokeGameTest {
    Character c1;
    Character c2;
    Character c3;
    Character c4;
    ArrayList<Character> allies;
    ArrayList<Character> enemies;
    Gameboard g;
    MokeGame mg;

    @BeforeEach
    void runBefore() {
        c1 = new testCharacter("A", 100, 10, 2, 2, 2, "a dude", 0, 0);
        c2 = new testCharacter("B", 100, 100, 1, 2, 1, "strong", 0, 1);
        c3 = new testCharacter("C", 10, 50, 3,  4, 4, "abc", 2, 2);
        c4 = new testCharacter("D", 20, 10, 4,  3, 3, "abc", 3, 3);
        allies = new ArrayList<Character>();
        enemies = new ArrayList<Character>();
        allies.add(c1);
        allies.add(c3);
        enemies.add(c2);
        enemies.add(c4);
        mg = new MokeGame(allies, enemies);
    }

    @Test
    void testConstructor() {
        assertEquals(mg.getAllies(), allies);
        assertEquals(mg.getEnemies(), enemies);
        assertEquals(mg.getGameboard().getTiles().size(), 25);
    }

    @Test
    void testTurnOrder() {
        assertEquals(4, mg.getTurnOrder().size());
        assertEquals(c3, mg.getTurnOrder().get(0));
        assertEquals(c4, mg.getTurnOrder().get(1));
        assertEquals(c1, mg.getTurnOrder().get(2));
        assertEquals(c2, mg.getTurnOrder().get(3));
    }

    @Test
    void testNextTurn() {
        assertEquals(c3, mg.getCurrentCharacter());
        assertTrue(mg.getMoved());
        mg.endMove();
        assertFalse(mg.getMoved());
        mg.nextTurn();
        assertEquals(c4, mg.getCurrentCharacter());
        mg.nextTurn();
        mg.nextTurn();
        mg.nextTurn();
        assertEquals(c3, mg.getCurrentCharacter());
    }

    @Test
    void testCheckDead() {
        mg.checkDead();
        assertEquals(mg.getAllies(), allies);
        assertEquals(mg.getEnemies(), enemies);
        c1.Hurt(100);
        c4.Hurt(100);
        c2.Hurt(1);
        mg.checkDead();
        assertEquals(mg.getAllies().size(), 1);
        assertEquals(mg.getAllies().get(0), c3);
        assertEquals(mg.getEnemies().size(), 1);
        assertEquals(mg.getEnemies().get(0), c2);
        assertEquals(mg.getTurnOrder().size(), 2);
    }

    @Test
    void testSetCurrentCharacter() {
        mg.setCurrentCharacter(c2);
        assertEquals(mg.getTurnIndex(), 3);
        mg.setCurrentCharacter(c4);
        assertEquals(mg.getTurnIndex(), 1);
    }

    void testXY(int xPos, int yPos, Character c) {
        assertEquals(c.getX(), xPos);
        assertEquals(c.getY(), yPos);
    }
}
