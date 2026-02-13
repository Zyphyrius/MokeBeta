package model;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FilterTest {
    Character c1;
    Character c2;
    Character c3;
    Character c4;
    ArrayList<Character> allies;
    ArrayList<Character> enemies;
    Gameboard g;
    MokeGame mg;
    AllyFilter af;
    EnemyFilter ef;
    NoFilter nf;
    AliveFilter alivef;

    @BeforeEach
    void runBefore() {
        c1 = new testCharacter("A", 100, 10, 2, 2, 2, "a dude", 0, 0, 1);
        c2 = new testCharacter("B", 100, 100, 1, 2, 1, "strong", 0, 1, 1);
        c3 = new testCharacter("C", 10, 50, 3,  4, 4, "abc", 2, 2, 1);
        c4 = new testCharacter("D", 20, 10, 4,  3, 3, "abc", 3, 3, 1);
        allies = new ArrayList<Character>();
        enemies = new ArrayList<Character>();
        allies.add(c1);
        allies.add(c3);
        enemies.add(c2);
        enemies.add(c4);
        mg = new MokeGame(allies, enemies);
        af = new AllyFilter();
        ef = new EnemyFilter();
        nf = new NoFilter();
        alivef = new AliveFilter();
    }

    @Test
    void testAllyFilter() {
        af.characterFilter(MokeGame.getTurnOrder());
        assertEquals(af.characterFilter(MokeGame.getTurnOrder()), List.of(c3, c1));
    }

    @Test
    void testEnemyFilter() {
        assertEquals(ef.characterFilter(MokeGame.getTurnOrder()), List.of(c4, c2));
    }

    @Test
    void testNoFilter() {
        assertEquals(nf.characterFilter(MokeGame.getTurnOrder()), MokeGame.getTurnOrder());
    }

    @Test
    void testAliveFilter() {
        c1.hurt(1000, c1);
        c4.hurt(1000, c1);
        c2.hurt(5, c1);
        assertEquals(alivef.characterFilter(MokeGame.getTurnOrder()), List.of(c3, c2));
    }
}
