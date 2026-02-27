package model;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;

@ExcludeFromJacocoGeneratedReport
public class MWTrooperTest {
    Character mwt;
    Character c1;
    Character c2;
    MokeGame mg;

    @BeforeEach
    void runBefore() {
        mwt = new MWTrooper();
        c1 = new DummyCharacter("A", 100, 10, 2, 2, 2, "a dude", 3, 3, 1);
        c2 = new DummyCharacter("B", 100, 100, 1, 2, 1, "strong", 1, 1, 1);
        mwt.setX(0);
        mwt.setY(0);
        mg = new MokeGame(new ArrayList<Character>(List.of(c1, c2)), new ArrayList<Character>(List.of(mwt)));
    }

    @Test
    void testAttackCloseRange() {
        mwt.attack(c2);
        assertEquals(80, c2.getHealth());
        assertEquals(0, mwt.getAttacksLeft());
    }

    @Test
    void testAttackNormal() {
        mwt.attack(c1);
        assertEquals(70, c1.getHealth());
        assertEquals(0, mwt.getAttacksLeft());
    }

    @Test
    void testToString() {
        assertEquals("Murky Water Trooper - A ranged unit that deals less damage up close", mwt.toString());
    }

    @Test
    void testCopy() {
        assertEquals(mwt.getClass(), mwt.newCopy().getClass());
    }
}
