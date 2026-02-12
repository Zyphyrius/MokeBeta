package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CharacterTest {
    Character c1;
    Character c2;
    Gameboard g1;

    @BeforeEach
    void runBefore() {
        c1 = new testCharacter("A", 100, 10, 2, 2, 2, "a dude", 0, 0);
        c2 = new testCharacter("B", 100, 110, 1, 2, 1, "strong", 4, 4);
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

    @Test
    void testHurt() {
        c1.Hurt(10);
        assertEquals(90, c1.getHealth());
        c1.Hurt(-10);
        assertEquals(90, c1.getHealth());
        c1.Hurt(30);
        assertEquals(60, c1.getHealth());
        c1.Hurt(70);
        assertEquals(0, c1.getHealth());
    }

    @Test
    void testIsDead() {
        c1.Hurt(99);
        assertFalse(c1.isDead());
        c1.Hurt(1);
        assertTrue(c1.isDead());
    }

    @Test
    void testHeal() {
        c1.Heal(100);
        assertEquals(100, c1.getHealth());
        c1.Hurt(50);
        c1.Heal(60);
        assertEquals(100, c1.getHealth());
        c1.Hurt(100);
        c1.Heal(10);
        assertEquals(0, c1.getHealth());
        assertTrue(c1.isDead());
    }

    @Test
    void testAttack() {
        c1.attack(c2);
        assertEquals(90, c2.getHealth());
        c1.attack(c2);
        assertEquals(80, c2.getHealth());
        c2.attack(c1);
        assertEquals(80, c2.getHealth());
        assertEquals(0, c1.getHealth());
    }

    @Test
    void testMoveUp() {
        testXY(4, 4, c2);
        c2.moveUp();
        testXY(4, 3, c2);
    }

    @Test
    void testMoveDown() {
        testXY(0, 0, c1);
        c1.moveDown();
        testXY(0, 1, c1);
    }

    @Test
    void testMoveRight() {
        testXY(0, 0, c1);
        c1.moveRight();
        testXY(1, 0, c1);
    }

    @Test
    void testMoveLeft() {
        testXY(4, 4, c2);
        c2.moveLeft();
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
