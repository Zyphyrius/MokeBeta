package model;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BarcelonaBeefBoggerTest {
    Character bbb;
    Character c1;
    Character c2;
    Character c3;
    MokeGame mg;

    @BeforeEach
    void runBefore() {
        bbb = new BarcelonaBeefBogger();
        c1 = new DummyCharacter("A", 50, 10, 2, 2, 2, "a dude", 2, 2, 1);
        c2 = new DummyCharacter("B", 300, 100, 1, 2, 1, "strong", 3, 3, 1);
        c3 = new DummyCharacter("C", 100, 100, 1, 2, 1, "also strong", 4, 3, 1);
        mg = new MokeGame(new ArrayList<Character>(List.of(bbb, c2, c3)), new ArrayList<Character>(List.of(c1)));
        c2.hurt(100, c1);
        c3.hurt(60, c1);
    }

    @Test
    void testNormalAttack() {
        bbb.attack(c1);
        assertEquals(40, c3.getHealth());
        assertEquals(200, c2.getHealth());
        assertEquals(10, c1.getHealth());
    }

    @Test 
    void testKillAttack() {
        c1.hurt(10, bbb);
        bbb.attack(c1);
        assertEquals(90, c3.getHealth());
        assertEquals(200, c2.getHealth());
    }
}
