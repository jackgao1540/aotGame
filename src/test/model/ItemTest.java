package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ItemTest {
    @Test
    public void testGetPrice() {
        String name = "sword";
        int price = 10;
        Item i = new Item(name, price);
        assertEquals(i.getPrice(), price);
    }

    @Test
    public void testGetName() {
        String name = "sword";
        int price = 10;
        Item i = new Item(name, price);
        assertEquals(i.getName(), name);
    }
}
