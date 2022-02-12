package ui;

import model.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

// AttackOnTitanApp, runs the UI for the game
public class AttackOnTitanApp {
    private Player player;              // Is the player object for this instance of the game
    private ArrayList<Item> shopItems;  // Is the list of items for the game
    private ArrayList<Titan> titans;    // Is the list of titans
    private Scanner input;              // Is the scanner used for getting user input

    public static final Integer MAX_CHARS = 26;     // Maximum length of player name
    public static final Integer MIN_CHARS = 2;      // Minimum length of player name
    public static final String quitKey = "q";       // key for exiting
    public static final String shopkey = "s";       // key for entering shop
    public static final String deleteTitanKey = "d";// key for defeating a titan
    public static final String checkTitansKey = "c";// key for checking the current titans

    // EFFECTS: runs the initialization of the UI
    public AttackOnTitanApp() {
        init();
    }

    // EFFECTS: waits for the user to press enter
    public void pressEnter() {
        System.out.println("ENTER to continue...");
        try {
            System.in.read();
        } catch (Exception e) {
            //nothing
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes the player variable and the two lists, and runs the game
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

    // MODIFIES: this
    // EFFECTS: runs the game and gets input while the user hasn't quit
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

    // EFFECTS: returns true if at least one titan is alive
    public boolean anAliveTitan() {
        for (int i = 0; i < titans.size(); i++) {
            if (!titans.get(i).isDead()) {
                return true;
            }
        }
        return false;
    }

    // MODIFIES: this
    // EFFECTS: handles the user input
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

    // EFFECTS: prints out the statuses of the titans
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

    // MODIFIES: this
    // EFFECTS: gets user input and deletes a titan from the list
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

    // EFFECTS: displays the list of items and player money
    public void displayShop() {
        System.out.println("------SHOP------");
        System.out.println("Player Money: $" + getPlayerMoney());
        for (int i = 0; i < shopItems.size(); i++) {
            System.out.println(shopItems.get(i).getName() + "-> $" + shopItems.get(i).getPrice());
        }
    }

    // MODIFIES: this
    // EFFECTS: displays the shop and gets user input to buy items or exit
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

    // MODIFIES: this
    // EFFECTS: gets user input and adds items to their inventory
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

    // EFFECTS: displays the menu and relevant information for the player
    public void showMenu() {
        System.out.println("Player name: " + player.getName() + ", Money: $" + getPlayerMoney());
        showPlayerItems(player.getItems());
        showAliveTitans();
        System.out.println("Check titans that are attacking: " + checkTitansKey);
        System.out.println("Defeat a titan: " + deleteTitanKey);
        System.out.println("Check Shop: " + shopkey);
        System.out.println("Head home (if titans are still alive the town will die): " + quitKey);
    }

    // EFFECTS: shows list of currently alive titans
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

    // EFFECTS: shows the player's inventory
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

    // EFFECTS: returns the player's money as a string to be displayed
    public String getPlayerMoney() {
        return ((Integer)player.getMoney()).toString();
    }

    // EFFECTS: checks if a given name is valid
    public boolean validName(String s) {
        if (s.length() >= MAX_CHARS || s.length() <= MIN_CHARS) {
            return false;
        }
        if (s.contains(" ")) {
            return false;
        }
        return true;
    }

    // MODIFIES: this
    // EFFECTS: initializes the shop with a list of items
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

    // MODIFIES: this
    // EFFECTS; initializes the list of titans attacking the village
    public void initTitans() {
        titans = new ArrayList<>(Arrays.asList(
                new Titan("Eren"),
                new Titan("Mikasa"),
                new Titan("Armin"),
                new Titan("Reiner"),
                new Titan("Jeke")));
    }
}
