package model;

import model.player.*;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {

    @Test
    public void testGets() {
        String name = "jack";
        ArrayList<Item> i = new ArrayList<>();
        int money = Player.DEFAULT_MONEY;
        Player theGreatest = new Player(name, null);
        assertEquals(theGreatest.getItems(), i);
        assertEquals(theGreatest.getName(), name);
        assertEquals(theGreatest.getMoney(), money);
    }

    @Test
    public void testMakePurchaseAndAddItem() {
        String name = "jack";
        ArrayList<Item> i = new ArrayList<>();
        int money = Player.DEFAULT_MONEY;
        Player theGreatest = new Player(name, null);
        Item item = new Item("s", 100, 0, 0, false, 0, 0, false, 0);
        theGreatest.makePurchase(item);
        assertEquals(theGreatest.getMoney(), 0);
        i.add(item);
        assertEquals(i, theGreatest.getItems());
    }

    @Test
    public void testValidName() {
        ArrayList <String> invalidNames = new ArrayList<>(
             Arrays.asList("", " a", "a ", "jack gao", "11111111111111111111111111"
        ));
        ArrayList <String> validNames = new ArrayList<>(
             Arrays.asList("aaa", "jackgao1540", "1111111111111111111111111"
        ));
        Player legend27 = new Player("thelegend27", null);
        for(String s : invalidNames) assertFalse(legend27.validName(s));
        for(String s : validNames) assertTrue(legend27.validName(s));
    }

    @Test
    public void testGetMoneyString() {
        int num = 123;
        String ans = "123";
        Player p = new Player("name", null);
        p.addMoney(num - p.DEFAULT_MONEY);
        assertEquals(p.getMoneyString(), ans);
    }

    @Test
    public void testAddMoney() {
        Player p = new Player("name", null);
        int ans = 123;
        p.addMoney(ans - p.DEFAULT_MONEY);
        assertEquals(ans, p.getMoney());
    }
}
