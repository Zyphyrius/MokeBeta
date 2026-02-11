package model;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MokeGameTest {
    Character c1;
    Character c2;
    Character c3;
    Character c4;

    @BeforeEach
    void runBefore() {
        c1 = new testCharacter("A", 100, 10, 2, 2, 2, "a dude", 0, 0);
        c2 = new testCharacter("B", 100, 100, 1, 2, 1, "strong", 0, 1);
        c3 = new testCharacter("C", 10, 50, 3,  4, 4, "abc", 1, 0);
        c4 = new testCharacter("D", 20, 10, 4,  3, 3, "abc", 1, 1);
    }

    @Test
    void sampleTest() {
        assertTrue(true);
    }
}
