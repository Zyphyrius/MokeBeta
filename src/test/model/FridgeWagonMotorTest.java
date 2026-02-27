package model;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FridgeWagonMotorTest {
    Character fwm;
    Character c1;

    @BeforeEach
    void runBefore() {
        fwm = new FridgeWagonMotor();
        c1 = new DummyCharacter("A", 100, 10, 2, 2, 2, "a dude", 1, 1, 1);
    }

    @Test
    void testAttack() {
        fwm.attack(c1);
        assertEquals(75, c1.getHealth());
        int halfMaxHP = (int) (fwm.getMaxHealth() * 0.5);
        fwm.hurt(halfMaxHP, fwm);
        fwm.attack(c1);
        assertEquals(25, c1.getHealth());
    }

    @Test
    void testToString() {
        assertEquals("Fridge Wagon Motor - A melee unit that moves fast and deals extra damage at low hp", 
                fwm.toString());
    }

    @Test
    void testCopy() {
        assertEquals(fwm.getClass(), fwm.newCopy().getClass());
    }
}
