package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TitanTest {
    @Test
    public void testGets() {
        Titan t = new Titan("bob");
        assertFalse(t.isDead());
        assertEquals(t.getName(), "bob");
    }

    @Test
    public void testMakeDead() {
        Titan t = new Titan("joe");
        t.makeDead();
        assertTrue(t.isDead());
    }
}
