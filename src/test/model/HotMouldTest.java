package model;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class HotMouldTest {
    Character hm;
    Character c1;
    Character c2;
    MokeGame mg;

    @BeforeEach
    void runBefore() {
        hm = new HotMould();
        c1 = new DummyCharacter("A", 100, 10, 2, 2, 2, "a dude", 2, 2, 1);
        c2 = new DummyCharacter("B", 100, 100, 1, 2, 1, "strong", 3, 3, 1);
        hm.setX(0);
        hm.setY(0);
        mg = new MokeGame(new ArrayList<Character>(List.of(hm, c1)), new ArrayList<Character>(List.of(c2)));
    }

    @Test
    void testHurt() {
        hm.attack(c2);
        assertEquals(70, c2.getHealth());
    }

    @Test
    void testHeal() {
        c1.hurt(40, c1);
        assertEquals(60, c1.getHealth());
        hm.attack(c1);
        assertEquals(90, c1.getHealth());
    }
}
