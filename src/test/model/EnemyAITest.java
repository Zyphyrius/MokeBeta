package model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EnemyAITest {
    Character c1;
    Character c2;
    Character c3;
    Character c4;
    Character c5;
    ArrayList<Character> allies;
    ArrayList<Character> enemies;
    MokeGame mg;
    EnemyAI enemyAI;

    @BeforeEach
    void runBefore() {
        c1 = new DummyCharacter("A", 100, 10, 2, 4, 2, "a dude", 0, 0, 2);
        c1.setAttackFilter(new AllyFilter());
        c2 = new DummyCharacter("B", 100, 100, 1, 3, 1, "strong", 0, 1, 1);
        c2.setAttackFilter(new AllyFilter());
        c3 = new DummyCharacter("C", 100, 50, 3,  2, 4, "abc", 1, 2, 1);
        c4 = new DummyCharacter("D", 90, 10, 4,  1, 3, "abc", 2, 2, 1);
        c5 = new DummyCharacter("E", 100, 10, 1,  1, 2, "far away", 4, 4, 1);
        allies = new ArrayList<Character>();
        enemies = new ArrayList<Character>();
        allies.add(c3);
        allies.add(c4);
        enemies.add(c1);
        enemies.add(c2);
        enemies.add(c5);
        mg = new MokeGame(allies, enemies); 
        enemyAI = new EnemyAI(mg, true);
        mg.getGameboard().placeCharacter(0, 0, c1);
        mg.getGameboard().placeCharacter(0, 1, c2);
        mg.getGameboard().placeCharacter(1, 2, c3);
        mg.getGameboard().placeCharacter(2, 2, c4);
        mg.getGameboard().placeCharacter(4, 4, c5);
    }

    @Test
    void testConstructor() {
        assertTrue(enemyAI.getNeededForGame());
        enemyAI.setNeededForGame(false);
        assertFalse(enemyAI.getNeededForGame());
        assertFalse(enemyAI.getMoved());
        assertFalse(enemyAI.getAttacked());
    }

    @Test
    void testDetermineAction() {
        assertEquals(2, enemyAI.determineAction(c1));
        enemyAI.setMoved(true);
        assertEquals(1, enemyAI.determineAction(c1));
        enemyAI.setAttacked(true);
        assertEquals(4, enemyAI.determineAction(c1));
    }

    @Test
    void testDetermineActionZeroStats() {
        c1.setMove(0);
        assertEquals(1, enemyAI.determineAction(c1));
        c1.setAttacks(0);
        assertEquals(4, enemyAI.determineAction(c1));
    }

    @Test
    void testDetermineAttackNoneInRange() {
        c1.setAttacksLeft(1);
        assertFalse(enemyAI.getAttacked());
        c1.setRange(0);
        assertEquals(0, enemyAI.determineAttack(c1));
        assertTrue(enemyAI.getAttacked());
    }

    @Test
    void testDetermineAttackOneInRange() {
        c2.setAttacksLeft(1);
        assertEquals(1, enemyAI.determineAttack(c2));
        assertTrue(enemyAI.getAttacked());
    }

    @Test
    void testDetermineAttackMultInRange() {
        assertEquals(2, enemyAI.determineAttack(c1));
        assertFalse(enemyAI.getAttacked());
        c1.setAttacksLeft(1);
        c3.hurt(50, c3);
        assertEquals(1, enemyAI.determineAttack(c1));
        assertTrue(enemyAI.getAttacked());
    }

    @Test
    void testDetermineAttackBerserker() {
        c1 = new MWBerserker();
        c1.setX(0);
        c1.setY(2);
        c1.setAttacksLeft(1);
        c1.setMovesLeft(1);
        assertEquals(1, enemyAI.determineAttack(c1));
        assertFalse(enemyAI.getAttacked());
        c1.setMovesLeft(0);
        assertEquals(1, enemyAI.determineAttack(c1));
        assertTrue(enemyAI.getAttacked());
    }

    @Test
    void testDetermineMoveInRange() {
        c1.setMovesLeft(2);
        assertFalse(enemyAI.getMoved());
        assertEquals(0, enemyAI.determineMove(c1));
        assertTrue(enemyAI.getMoved());
    }

    @Test
    void testDeterineMoveOutOfRangeDownRight() {
        c1.setMovesLeft(2);
        c1.setRange(1);
        assertEquals(4, enemyAI.determineMove(c1));
        assertFalse(enemyAI.getMoved());
        c1.moveRight();
        assertEquals(2, enemyAI.determineMove(c1));
        assertTrue(enemyAI.getMoved());
    }

    @Test
    void testDeterineMoveOutOfRangeUpLeft() {
        c5.setMovesLeft(2);
        assertEquals(1, enemyAI.determineMove(c5));
        assertFalse(enemyAI.getMoved());
        c5.moveUp();
        assertEquals(3, enemyAI.determineMove(c5));
        assertTrue(enemyAI.getMoved());
    }

    @Test
    void testDeterineMoveVertBlocked() {
        mg.getGameboard().placeCharacter(4, 3, c1);
        c5.setMovesLeft(2);
        assertEquals(3, enemyAI.determineMove(c5));
        assertFalse(enemyAI.getMoved());
        c5.moveLeft();
        assertEquals(1, enemyAI.determineMove(c5));
        assertTrue(enemyAI.getMoved());
    }

    @Test
    void testDetermineMoveBlockedUpLeft() {
        mg.getGameboard().placeCharacter(4, 3, c1);
        mg.getGameboard().placeCharacter(3, 4, c2);
        c5.setMovesLeft(2);
        assertEquals(0, enemyAI.determineMove(c5));
        assertTrue(enemyAI.getMoved());
    }

    @Test
    void testDetermineMoveBlockedDownRight() {
        mg.getGameboard().placeCharacter(4, 5, c1);
        mg.getGameboard().placeCharacter(5, 4, c2);
        c4.setX(6);
        c4.setY(6);
        c5.setMovesLeft(2);
        assertEquals(0, enemyAI.determineMove(c5));
        assertTrue(enemyAI.getMoved());
    }

    
    @Test
    void testDetermineMoveLeftBlockedUp() {
        mg.getGameboard().placeCharacter(3, 4, c1);
        c4.setX(1);
        c4.setY(3);
        c5.setMovesLeft(2);
        assertEquals(1, enemyAI.determineMove(c5));
    }

    @Test
    void testDetermineMoveLeftBlockedDown() {
        mg.getGameboard().placeCharacter(3, 4, c1);
        c4.setX(1);
        c4.setY(5);
        c5.setMovesLeft(2);
        assertEquals(2, enemyAI.determineMove(c5));
    }
    
    @Test
    void testDetermineMoveRightBlockedUp() {
        mg.getGameboard().placeCharacter(5, 4, c1);
        c4.setX(6);
        c4.setY(3);
        c5.setMovesLeft(2);
        assertEquals(1, enemyAI.determineMove(c5));
    }

     @Test
    void testDetermineMoveRightBlockedDown() {
        mg.getGameboard().placeCharacter(5, 4, c1);
        c4.setX(6);
        c4.setY(5);
        c5.setMovesLeft(2);
        assertEquals(2, enemyAI.determineMove(c5));
    }
}
