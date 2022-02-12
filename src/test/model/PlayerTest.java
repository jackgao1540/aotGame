package model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlayerTest {

    @Test
    public void testGets() {
        String name = "jack";
        ArrayList<Item> i = new ArrayList<>();
        int money = Player.DEFAULT_MONEY;
        Player theGreatest = new Player(name);
        assertEquals(theGreatest.getItems(), i);
        assertEquals(theGreatest.getName(), name);
        assertEquals(theGreatest.getMoney(), money);
    }

    @Test
    public void testMakePurchaseAndAddItem() {
        String name = "jack";
        ArrayList<Item> i = new ArrayList<>();
        int money = Player.DEFAULT_MONEY;
        Player theGreatest = new Player(name);
        Item item = new Item("s", 100);
        theGreatest.makePurchase(item);
        assertEquals(theGreatest.getMoney(), 0);
        i.add(item);
        assertEquals(i, theGreatest.getItems());
    }
}
