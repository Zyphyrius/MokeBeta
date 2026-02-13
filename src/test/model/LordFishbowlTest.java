package model;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LordFishbowlTest {
    Character lfb;
    Character c1;
    Character c2;
    MokeGame mg;

    @BeforeEach
    void runBefore() {
        lfb = new LordFishbowl();
        c1 = new DummyCharacter("A", 100, 10, 2, 2, 2, "a dude", 2, 2, 1);
        c2 = new DummyCharacter("B", 100, 100, 1, 2, 1, "strong", 3, 3, 1);
        lfb.setX(0);
        lfb.setY(0);
        mg = new MokeGame(new ArrayList<Character>(List.of(lfb)), new ArrayList<Character>(List.of(c1, c2)));
    }

    @Test
    void testHurtInRange() {
        lfb.hurt(10, c1);
        assertEquals(90, lfb.getHealth());
        lfb.hurt(-5, c1);
        assertEquals(90, lfb.getHealth());
        lfb.hurt(100, c1);
        assertEquals(0, lfb.getHealth());
    }

    @Test
    void testHurtOutOfRange() {
        lfb.hurt(10, c2);
        assertEquals(100, lfb.getHealth());
    }
}
