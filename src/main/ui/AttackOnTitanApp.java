package ui;

import model.*;
import persistence.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

// AttackOnTitanApp, runs the UI for the game
public class AttackOnTitanApp {
    private static final String JSON_STORE = "./data/game.json";
    private Player player;              // Is the player object for this instance of the game
    private ArrayList<Item> shopItems;  // Is the list of items for the game
    private ArrayList<Titan> titans;    // Is the list of titans
    private GameState gs;               // Is the GameState (saveable state that represents the game)
    private Scanner input;              // Is the scanner used for getting user input
    private JsonWriter jsonWriter;      // Object that can write (save) the gamestate
    private JsonReader jsonReader;      // Object that can read the gamestate from JSON
    private int runState;               // The running state of the program, used upon exit
    private static final String quitKey = "q";       // key for exiting
    private static final String shopkey = "s";       // key for entering shop
    private static final String deleteTitanKey = "d";// key for defeating a titan
    private static final String checkTitansKey = "c";// key for checking the current titans
    private static final String saveKey = "o";       // key for saving
    private static final String loadKey = "p";       // key for loading gamestate
    private static final String saveQuitKey = "k";   // key for saving and quitting

    // EFFECTS: runs the initialization of the UI
    public AttackOnTitanApp() {
        init();
    }

    // MODIFIES: this
    // EFFECTS: initializes the gamestate and runs the game
    public void init() {
        input = new Scanner(System.in);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        input.useDelimiter("\n");
        player = new Player("null");
        //get player name
        System.out.println("Please enter your name: ");
        String s = input.next();
        while (!player.validName(s)) {
            System.out.println("Please enter a valid name (no spaces, reasonable length)");
            s = input.next();
        }

        //initialize key variables
        player = new Player(s);
        initTitans();
        gs = new GameState(player, titans);
        initShop();
        runGame();
    }

    // EFFECTS: saves the gamestate to file
    private void saveGameState() {
        try {
            jsonWriter.open();
            jsonWriter.write(gs);
            jsonWriter.close();
            System.out.println("Saved game to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads gamestate from file
    private void loadGameState() {
        try {
            gs = jsonReader.read();
            System.out.println("Loaded save from " + JSON_STORE + ", welcome back, " + gs.getPlayer().getName() + "!");
            this.player = gs.getPlayer();
            this.titans = gs.getTitans();
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }


    // MODIFIES: this
    // EFFECTS: runs the game and gets input while the user hasn't quit
    public void runGame() {
        runState = 1;
        String in;
        while (runState == 1) {
            showMenu();
            in = input.next();
            in = in.toLowerCase();
            if (in.equals(quitKey)) {
                runState = -1;
            } else {
                handleInput(in);
            }
        }
        if (runState == -1) {
            if (!anAliveTitan()) {
                System.out.println("Congratulations! You defeated the titans.");
            } else {
                System.out.println("Oops! You must have missed a few! The town is destroyed...");
            }
        } else {
            //saved and quitted
            System.out.println("Thanks for playing, please come back soon!");
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
        } else if (in.equals(saveKey)) {
            saveGameState();
        } else if (in.equals(loadKey)) {
            loadGameState();
        } else if (in.equals(saveQuitKey)) {
            saveGameState();
            runState = 0;
        } else {
            System.out.println("Please enter a valid command.");
        }
    }

    // EFFECTS: prints out the statuses of the titans, and reward if alive
    public void checkTitans() {
        System.out.println("Titans: ");
        for (int i = 0; i < titans.size(); i++) {
            Titan cur = titans.get(i);
            if (cur.isDead()) {
                System.out.println(cur.getName() + " is dead.");
            } else {
                System.out.println(cur.getName() + " is still alive. Bounty of $" + cur.getRewardString() + ".");
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
                System.out.println("You earned $" + cur.getRewardString() + "!");
                player.addMoney(cur.getReward());
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
        System.out.println("Player Money: $" + player.getMoneyString());
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
        System.out.println("Player name: " + player.getName() + ", Money: $" + player.getMoneyString());
        showPlayerItems(player.getItems());
        showAliveTitans();
        System.out.println("Check titans that are attacking: " + checkTitansKey);
        System.out.println("Defeat a titan: " + deleteTitanKey);
        System.out.println("Check Shop: " + shopkey);
        System.out.println("Save Game: " + saveKey);
        System.out.println("Load Game: " + loadKey);
        System.out.println("Save and quit (without ending your game session): " + saveQuitKey);
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
                System.out.print(a.get(i).getName() + (i == a.size() - 1 ? "" : ", "));
            }
            System.out.println();
        }
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
                new Titan("Eren", 500),
                new Titan("Mikasa", 250),
                new Titan("Armin", 250),
                new Titan("Reiner", 100),
                new Titan("Jeke", 100)));
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
}
