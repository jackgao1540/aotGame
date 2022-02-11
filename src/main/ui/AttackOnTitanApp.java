package ui;

import model.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

// AttackOnTitanApp
public class AttackOnTitanApp {
    private Player player;
    private ArrayList<Item> shopItems;
    private ArrayList<Titan> titans;
    private Scanner input;

    public static final Integer MAX_CHARS = 26;
    public static final Integer MIN_CHARS = 2;
    public static final String quitKey = "q";
    public static final String shopkey = "s";
    public static final String deleteTitanKey = "d";
    public static final String checkTitansKey = "c";

    public AttackOnTitanApp() {
        init();
    }

    public void pressEnter() {
        System.out.println("ENTER to continue...");
        try {
            System.in.read();
        } catch (Exception e) {
            //nothing
        }
    }

    public void init() {
        input = new Scanner(System.in);
        input.useDelimiter("\n");

        //get player name
        System.out.println("Please enter your name: ");
        String s = input.next();
        while (!validName(s)) {
            System.out.println("Please enter a valid name (no spaces, reasonable length)");
            s = input.next();
        }

        //initialize key variables
        player = new Player(s);
        initTitans();
        initShop();
        runGame();
    }

    public void runGame() {
        boolean isRunning = true;
        String in;
        while (isRunning) {
            showMenu();
            in = input.next();
            in = in.toLowerCase();
            if (in.equals(quitKey)) {
                isRunning = false;
            } else {
                handleInput(in);
            }
        }

        if (!anAliveTitan()) {
            System.out.println("Congratulations! You defeated the titans.");
        } else {
            System.out.println("Oops! You must have misse a few! The town is destroyed...");
        }
    }

    public boolean anAliveTitan() {
        for (int i = 0; i < titans.size(); i++) {
            if (!titans.get(i).isDead()) {
                return true;
            }
        }
        return false;
    }

    public void handleInput(String in) {
        if (in.equals(checkTitansKey)) {
            checkTitans();
        } else if (in.equals(deleteTitanKey)) {
            deleteTitan();
        } else if (in.equals(shopkey)) {
            shop();
        } else {
            System.out.println("Please enter a valid command.");
        }
    }

    public void checkTitans() {
        System.out.println("Titans: ");
        for (int i = 0; i < titans.size(); i++) {
            Titan cur = titans.get(i);
            if (cur.isDead()) {
                System.out.println(cur.getName() + " is dead.");
            } else {
                System.out.println(cur.getName() + " is still rumbling around.");
            }
        }
        pressEnter();
    }

    public void deleteTitan() {
        System.out.println("Please enter the name of the titan you shall defeat.");
        String titanName = input.next();
        titanName = titanName.toLowerCase();
        boolean found = false;
        for (int i = 0; i < titans.size(); i++) {
            Titan cur = titans.get(i);
            if (cur.getName().toLowerCase().equals(titanName)) {
                found = true;
                cur.makeDead();
                System.out.println(cur.getName() + " was killed in battle...");
            }
        }
        if (!found) {
            System.out.println("Ymir did not make a titan with that name.");
        }
        pressEnter();
    }

    public void displayShop() {
        System.out.println("------SHOP------");
        System.out.println("Player Money: $" + getPlayerMoney());
        for (int i = 0; i < shopItems.size(); i++) {
            System.out.println(shopItems.get(i).getName() + "-> $" + shopItems.get(i).getPrice());
        }
    }

    public void shop() {
        boolean inShop = true;
        String cmd;
        while (inShop) {
            displayShop();
            System.out.println("Press b to buy an item, e to exit shop.");
            cmd = input.next();
            cmd = cmd.toLowerCase();
            if (cmd.equals("e")) {
                inShop = false;
            } else if (cmd.equals("b")) {
                buyItem();
            } else {
                System.out.println("Please enter a valid command.");
            }
        }
        System.out.println("Thanks for visiting the shop!");
        pressEnter();
    }

    public void buyItem() {
        System.out.println("Please enter the name of the item you would buy.");
        String itemName = input.next().toLowerCase();
        boolean found = false;
        for (int i = 0; i < shopItems.size(); i++) {
            Item cur = shopItems.get(i);
            if (cur.getName().toLowerCase().equals(itemName)) {
                found = true;
                if (cur.getPrice() <= player.getMoney()) {
                    player.makePurchase(cur);
                    System.out.println("Purchase successful!");
                } else {
                    System.out.println("Insufficient funds.");
                }
                break;
            }
        }
        if (!found) {
            System.out.println("Item not found.");
        }
        pressEnter();
    }

    public void showMenu() {
        System.out.println("Player name: " + player.getName() + ", Money: $" + getPlayerMoney());
        showPlayerItems(player.getItems());
        showAliveTitans();
        System.out.println("Check titans that are attacking: " + checkTitansKey);
        System.out.println("Defeat a titan: " + deleteTitanKey);
        System.out.println("Check Shop: " + shopkey);
        System.out.println("Head home (if titans are still alive the town will die): " + quitKey);
    }

    public void showAliveTitans() {
        System.out.print("Currently alive titans: ");
        boolean none = true;
        for (int i = 0; i < titans.size(); i++) {
            Titan cur = titans.get(i);
            if (!cur.isDead()) {
                none = false;
                System.out.print(cur.getName() + ' ');
            }
        }
        if (none) {
            System.out.println("None! good job soldier.");
        } else {
            System.out.println();
        }
    }

    public void showPlayerItems(ArrayList<Item> a) {
        System.out.print(player.getName() + "'s items: ");
        if (a.isEmpty()) {
            System.out.println(" No items in inventory.");
        } else {
            for (int i = 0; i < a.size(); i++) {
                System.out.print(a.get(i).getName() + " ");
            }
            System.out.println();
        }
    }

    public String getPlayerMoney() {
        return ((Integer)player.getMoney()).toString();
    }

    boolean validName(String s) {
        if (s.length() >= MAX_CHARS || s.length() <= MIN_CHARS) {
            return false;
        }
        if (s.contains(" ")) {
            return false;
        }
        return true;
    }

    public void initShop() {
        shopItems = new ArrayList<>(Arrays.asList(
                new Item("Rusty Kitchen Knife", 1),
                new Item("Meat Pie for Sasha", 3),
                new Item("Zeke's Baseball", 4),
                new Item("Sword", 10),
                new Item("ODM Gear", 50),
                new Item("Thunder Spear",  100),
                new Item("Founding Titan Fluid", 999)));
    }

    public void initTitans() {
        titans = new ArrayList<>(Arrays.asList(
                new Titan("Eren"),
                new Titan("Mikasa"),
                new Titan("Armin"),
                new Titan("Reiner"),
                new Titan("Jeke")));
    }
}
