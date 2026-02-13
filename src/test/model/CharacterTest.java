package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CharacterTest {
    Character c1;
    Character c2;
    Character c3;
    ArrayList<Character> allies;
    ArrayList<Character> enemies;
    Gameboard g;
    MokeGame mg;
    


    @BeforeEach
    void runBefore() {
        c1 = new testCharacter("A", 100, 10, 4, 2, 2, "a dude", 0, 0, 1);
        c2 = new testCharacter("B", 100, 110, 1, 2, 1, "strong", 4, 4, 1);
        c3 = new testCharacter("C", 100, 30, 2, 2, 1, "normal", 3, 3, 1);
        allies = new ArrayList<Character>();
        enemies = new ArrayList<Character>();
        allies.add(c1);
        allies.add(c3);
        enemies.add(c2);
        mg = new MokeGame(allies, enemies);
    }

    @Test
    void testConstructor() {
        assertEquals("A", c1.getName());
        assertEquals(100, c1.getHealth());
        assertEquals(10, c1.getAttack());
        assertEquals(4, c1.getRange());
        assertEquals(2, c1.getSpeed());
        assertEquals(2, c1.getMove());
        assertEquals("a dude", c1.getAbility());
        assertEquals(0, c1.getX());
        assertEquals(0, c1.getY());
        assertEquals(1, c1.getAttacks());
    }

    @Test
    void testHurt() {
        c1.hurt(10, c1);
        assertEquals(90, c1.getHealth());
        c1.hurt(-10, c1);
        assertEquals(90, c1.getHealth());
        c1.hurt(30, c1);
        assertEquals(60, c1.getHealth());
        c1.hurt(70, c1);
        assertEquals(0, c1.getHealth());
    }

    @Test
    void testIsDead() {
        c1.hurt(99, c1);
        assertFalse(c1.isDead());
        c1.hurt(1, c1);
        assertTrue(c1.isDead());
    }

    @Test
    void testHeal() {
        c1.heal(100);
        assertEquals(100, c1.getHealth());
        c1.hurt(50, c1);
        c1.heal(10);
        assertEquals(60, c1.getHealth());
        c1.heal(70);
        assertEquals(100, c1.getHealth());
        c1.hurt(100, c1);
        c1.heal(10);
        assertEquals(0, c1.getHealth());
        assertTrue(c1.isDead());
    }

    @Test
    void testAttack() {
        c1.setAttacksLeft(1);
        c2.setAttacksLeft(1);
        c1.attack(c2);
        assertEquals(90, c2.getHealth());
        assertEquals(0, c1.getAttacksLeft());
        assertEquals(1, c2.getAttacksLeft());
        c1.attack(c2);
        assertEquals(80, c2.getHealth());
        c2.attack(c1);
        assertEquals(80, c2.getHealth());
        assertEquals(0, c1.getHealth());
    }

    @Test
    void testGetInRange() {
        assertEquals(c1.getInRange(new NoFilter()), List.of(c3, c2));
        assertEquals(c1.getInRange(new EnemyFilter()), List.of(c2));
        assertEquals(c1.getInRange(new AllyFilter()), List.of(c3));
        assertEquals(c2.getInRange(new AllyFilter()), List.of(c3));
        assertEquals(c3.getInRange(new NoFilter()), List.of(c2));
    }

    @Test
    void testMoveUp() {
        testXY(4, 4, c2);
        c2.setMovesLeft(1);
        c2.moveUp();
        assertEquals(0, c2.getMovesLeft());
        testXY(4, 3, c2);
    }

    @Test
    void testMoveDown() {
        testXY(0, 0, c1);
        c1.setMovesLeft(2);
        c1.moveDown();
        assertEquals(1, c1.getMovesLeft());
        testXY(0, 1, c1);
    }

    @Test
    void testMoveRight() {
        testXY(0, 0, c1);
        c2.setMovesLeft(2);
        c1.moveRight();
        assertEquals(1, c1.getMovesLeft());
        testXY(1, 0, c1);
    }

    @Test
    void testMoveLeft() {
        testXY(4, 4, c2);
        c2.setMovesLeft(1);
        c2.moveLeft();
        assertEquals(0, c2.getMovesLeft());
        testXY(3, 4, c2);
    }

    @Test
    void testMoveManyTimes() {
        c1.moveDown();
        c1.moveRight();
        c1.moveRight();
        c1.moveDown();
        c1.moveLeft();
        c1.moveUp();
        testXY(1, 1, c1);
    }

    void testXY(int xPos, int yPos, Character c) {
        assertEquals(c.getX(), xPos);
        assertEquals(c.getY(), yPos);
    }
}
