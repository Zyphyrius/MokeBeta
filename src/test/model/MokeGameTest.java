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
    MokeGame mg;

    @BeforeEach
    void runBefore() {
        c1 = new DummyCharacter("A", 100, 10, 2, 2, 2, "a dude", 0, 0, 1);
        c2 = new DummyCharacter("B", 100, 100, 1, 2, 1, "strong", 0, 1, 1);
        c3 = new DummyCharacter("C", 10, 50, 3,  4, 4, "abc", 2, 2, 1);
        c4 = new DummyCharacter("D", 20, 10, 4,  3, 3, "abc", 4, 4, 1);
        allies = new ArrayList<Character>();
        enemies = new ArrayList<Character>();
        allies.add(c1);
        allies.add(c3);
        enemies.add(c2);
        enemies.add(c4);
        mg = new MokeGame(allies, enemies);
        mg.startBoard();
    }

    @Test
    void testConstructor() {
        assertEquals(MokeGame.getAllies(), allies);
        assertEquals(MokeGame.getEnemies(), enemies);
        assertEquals(mg.getGameboard().getTiles().size(), 25);
    }

    @Test 
    void testConstructorEnemiesLarger() {
        enemies.add(c2);
        mg = new MokeGame(allies, enemies);
        assertEquals(mg.getGameboard().getTiles().size(), 36);
    }

    @Test
    void testTurnOrder() {
        assertEquals(4, MokeGame.getTurnOrder().size());
        assertEquals(c3, MokeGame.getTurnOrder().get(0));
        assertEquals(c4, MokeGame.getTurnOrder().get(1));
        assertEquals(c1, MokeGame.getTurnOrder().get(2));
        assertEquals(c2, MokeGame.getTurnOrder().get(3));
    }

    @Test
    void testNextTurn() {
        assertEquals(c3, mg.getCurrentCharacter());
        assertTrue(mg.canMove());
        mg.getCurrentCharacter().setMovesLeft(0);
        assertFalse(mg.canMove());
        assertTrue(mg.canAttack());
        mg.attackCharacter(c1);
        assertFalse(mg.canAttack());
        mg.nextTurn();
        assertTrue(mg.canMove());
        assertTrue(mg.canAttack());
        assertEquals(c4, mg.getCurrentCharacter());
        mg.nextTurn();
        mg.nextTurn();
        mg.nextTurn();
        assertEquals(c3, mg.getCurrentCharacter());
    }

    @Test 
    void testNextTurnStatuses() {
        c3.addStatus(new Stun(1));
        c4.addStatus(new Immobilize(1));
        mg.nextTurn();
        assertEquals(0, c3.getStatuses().size());
        assertEquals(1, c4.getStatuses().size());
        assertEquals(0, c4.getMovesLeft());
    }

    @Test
    void testFirstAliveCharacter() {
        c3.hurt(1000, c1);
        assertEquals(c4, mg.findFirstAlive());
        c2.hurt(1000, c1);
        assertEquals(c4, mg.findFirstAlive());
    }

    @Test
    void testFirstAliveCharacterStartLastIndex() {
        mg.setCurrentCharacter(c2);
        c2.hurt(1000, c1);
        c3.hurt(1000, c1);
        assertEquals(c4, mg.findFirstAlive());
    }

    @Test
    void testCheckDead() {
        mg.setCurrentCharacter(c1);
        mg.checkDead();
        assertEquals(MokeGame.getAllies(), allies);
        assertEquals(MokeGame.getEnemies(), enemies);
        assertEquals(mg.getCurrentCharacter(), c1);
        c1.hurt(100, c1);
        c4.hurt(100, c1);
        c2.hurt(1, c1);
        mg.checkDead();
        assertEquals(1, MokeGame.getAllies().size());
        assertEquals(c3, MokeGame.getAllies().get(0));
        assertEquals(1, MokeGame.getEnemies().size());
        assertEquals(c2, MokeGame.getEnemies().get(0));
        assertFalse(mg.isGameOver());
        assertEquals(2, MokeGame.getTurnOrder().size());
        assertEquals(c2, mg.getCurrentCharacter());
    }

    @Test
    void testCheckDeadWin() {
        mg.checkDead();
        assertFalse(mg.isGameOver());
        c2.hurt(1000, c1);
        mg.checkDead();
        assertFalse(mg.isGameOver());
        c4.hurt(1000, c1);
        mg.checkDead();
        assertTrue(mg.isGameOver());
        assertTrue(mg.didWin());
    }

    @Test
    void testCheckDeadLoss() {
        c1.hurt(1000, c1);
        c2.hurt(1000, c1);
        c3.hurt(1000, c1);
        mg.checkDead();
        assertTrue(mg.isGameOver());
        assertFalse(mg.didWin());
        c4.hurt(1000, c1);
        mg.checkDead();
        assertTrue(mg.isGameOver());
        assertFalse(mg.didWin());
    }

    @Test
    void testSetCurrentCharacter() {
        mg.setCurrentCharacter(c2);
        assertEquals(mg.getTurnIndex(), 3);
        mg.setCurrentCharacter(c4);
        assertEquals(mg.getTurnIndex(), 1);
    }

    @Test
    void testAttackCharacter() {
        assertTrue(mg.canAttack());
        mg.attackCharacter(c2);
        assertEquals(50, c2.getHealth());
        assertFalse(mg.canAttack());
    }

    @Test
    void testStartBoard() {
        assertEquals(c2, mg.getGameboard().findTile(0, 0).getCharacter());
        assertEquals(c4, mg.getGameboard().findTile(1, 0).getCharacter());
        assertEquals(c1, mg.getGameboard().findTile(4, 4).getCharacter());
        assertEquals(c3, mg.getGameboard().findTile(3, 4).getCharacter());
    }

    @Test
    void testMoveUp() {
        testMoveInstance("up", false, 0, 0, c2);
        testMoveInstance("up", true, 3, 3, c3);
    }

    @Test
    void testMoveDown() {
        testMoveInstance("down", false, 4, 4, c1);
        testMoveInstance("down", true, 1, 1, c4);
    }

    @Test
    void testMoveLeft() {
        testMoveInstance("left", false, 0, 0, c2);
        testMoveInstance("left", false, 1, 0, c4);
        testMoveInstance("left", true, 2, 4, c3);
    }

    @Test
    void testMoveRight() {
        testMoveInstance("right", false, 0, 0, c2);
        testMoveInstance("right", false, 4, 4, c1);
        testMoveInstance("right", true, 2, 0, c4); 
    }

    @Test
    void testCapsMoveAllOver() {
        testMoveInstance("UP", true, 4, 3, c1);
        testMoveInstance("UP", true, 4, 2, c1);
        testMoveInstance("YUP", false, 4, 2, c1);
        testMoveInstance("LEFT", true, 3, 2, c1);
        testMoveInstance("RIGHT", true, 4, 2, c1);
        testMoveInstance("RIGHT", false, 4, 2, c1);
        testMoveInstance("DOWN", true, 4, 3, c1);
    }

    @Test
    void testGameOverWin() {
        assertFalse(mg.isGameOver());
        mg.setGameOver(true);
        assertTrue(mg.isGameOver());
        assertTrue(mg.didWin());
    }

    @Test
    void testGameOverLoss() {
        assertFalse(mg.isGameOver());
        mg.setGameOver(false);
        assertTrue(mg.isGameOver());
        assertFalse(mg.didWin());
    }

    void testMoveInstance(String direction, boolean valid, int endX, int endY, Character c) {
        mg.setCurrentCharacter(c);
        if (valid) {
            assertTrue(mg.moveCharacter(direction));
        } else {
            assertFalse(mg.moveCharacter(direction));
        }
        assertEquals(c, mg.getGameboard().findTile(endX, endY).getCharacter());
    }
}
