package ui;

import model.*;
import model.buildings.*;
import model.Item;
import model.player.*;
import model.titans.*;
import persistence.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

// AttackOnTitanApp, runs the UI for the game
public class AttackOnTitanApp extends JFrame {
    private static final String JSON_STORE = "./data/game.json";
    private Player player;              // Is the player object for this instance of the game
    private ArrayList<Item> shopItems;  // Is the list of items for the game
    private ArrayList<Titan> titans;    // Is the list of titans
    private ArrayList<TownHouse> buildings;
    private TownHall townHall;
    private Shop shop;
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
    private static final String modifyBuildingsKey = "b";
    private static final String saveQuitKey = "k";   // key for saving and quitting

    //panels
    private GamePanel gamePanel;
    private MenuPanel menuPanel;

    // EFFECTS: runs the initialization of the UI
    public AttackOnTitanApp() {
        super("Attack On Titan");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(false);
        menuPanel = new MenuPanel(this);
        add(menuPanel);
        pack();
        setResizable(false);
        setVisible(true);
        init();
    }

    public void init() {

    }

    public void newGameState() {
        input = new Scanner(System.in);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        input.useDelimiter("\n");
        //get player name
        System.out.println("Please enter your name: ");
        String s = input.next();
        while (!Player.validName(s)) {
            System.out.println("Please enter a valid name (no spaces, reasonable length)");
            s = input.next();
        }

        //initialize key variables
        player = new Player(s, null);
        initTitans();
        initBuildings();
        townHall = new TownHall(null);
        shop = new Shop(null);
        gs = new GameState(player, titans, buildings, townHall, shop);
        setGameStates();
        initShop();
        runGame();
    }

    public void setGameStates() {
        player.setGameState(gs);
        for (Titan t: titans) {
            t.setGameState(gs);
        }
        for (TownHouse t: buildings) {
            t.setGameState(gs);
        }
        townHall.setGameState(gs);
        shop.setGameState(gs);
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
            player = gs.getPlayer();
            titans = gs.getTitans();
            buildings = gs.getBuildings();
            townHall = gs.getTownHall();
            shop = gs.getShop();
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
        return titans.size() > 0;
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
        } else if (in.equals(modifyBuildingsKey)) {
            modifyBuildings();
        } else {
            System.out.println("Please enter a valid command.");
        }
    }

    public void modifyBuildings() {
        System.out.println("Enter 0 to modify a townhouse, 1 for townhall, 2 for shop");
        int inp = input.nextInt();
        if (inp == 0) {
            System.out.println("Enter index and amount of hp to subtract.");
            int index = input.nextInt();
            int hp = input.nextInt();
            if (index >= buildings.size()) {
                System.out.println("Please enter a valid input");
            } else {
                buildings.get(index).subtractHP(hp);
            }

        } else if (inp == 1) {
            System.out.println("Please enter hp to subtract");
            int hp = input.nextInt();
            townHall.subtractHP(hp);
        } else if (inp == 2) {
            System.out.println("Please enter hp to subtract");
            int hp = input.nextInt();
            shop.subtractHP(hp);
        } else {
            System.out.println("Please enter a valid command.");
        }
    }

    // EFFECTS: prints out the statuses of the titans, and reward if alive
    public void checkTitans() {
        System.out.println("Titans: ");
        for (int i = 0; i < titans.size(); i++) {
            Titan cur = titans.get(i);
            System.out.println("Titan " +  i + " r: " + cur.getRewardString() + " x: " + cur.getX() + " y: " + cur.getY() + " w: " + cur.getWidth() + " h: " + cur.getHeight());
        }
        pressEnter();
    }

    // MODIFIES: this
    // EFFECTS: gets user input and deletes a titan from the list
    public void deleteTitan() {
        System.out.println("Index of titan to remove: ");
        int titanIndex = input.nextInt();
        if (titanIndex < titans.size()) {
            titans.remove(titanIndex);
        } else {
            System.out.println("Please enter a valid index");
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
        System.out.println("Number of titans: " + titans.size());
        System.out.println("Check titans that are attacking: " + checkTitansKey);
        System.out.println("Defeat a titan: " + deleteTitanKey);
        System.out.println("Check Shop: " + shopkey);
        System.out.println("Save Game: " + saveKey);
        System.out.println("Load Game: " + loadKey);
        System.out.println("Save and quit (without ending your game session): " + saveQuitKey);
        System.out.println("Head home (if titans are still alive the town will die): " + quitKey);
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

    public void initBuildings() {
        buildings = new ArrayList<TownHouse>(Arrays.asList(
                new TownHouse(100, 200, 0, null),
                new TownHouse(50, 500, 20, null),
                new TownHouse(700, 200, 300, null),
                new TownHouse(600, 300, 169, null),
                new TownHouse(600, 600, 196, null),
                new TownHouse(400, 800, 100, null),
                new TownHouse(100, 600, 90, null)));
    }

    // MODIFIES: this
    // EFFECTS: initializes the shop with a list of items
    public void initShop() {
        shopItems = new ArrayList<>(Arrays.asList(
                new Item("Rusty Kitchen Knife", 1, 1, 0, false, 0, 0, false, 0),
                new Item("Meat Pie for Sasha", 3, 0, 0, false, 0, 0, false, 0),
                new Item("Zeke's Baseball", 4, 2, 2, true, 5, 2, false, 0),
                new Item("Sword", 10, 10, 0, true, 0, 0, false, 0),
                new Item("ODM Gear", 50, 5, 50, false, 0, 0, true, 100),
                new Item("Thunder Spear",  100, 50, 0, true, 300, 30, false, 0),
                new Item("Mikasa's Red Scarf", 130, 200, 200, false, 0, 0, true, 500),
                new Item("Founding Titan Fluid", 999, 100000, 0, false, 0, 0, false, 0)));
    }

    // MODIFIES: this
    // EFFECTS; initializes the list of titans attacking the village
    public void initTitans() {
        titans = new ArrayList<>(Arrays.asList(
                new Titan(1000, 25, 25, 50, 50, null),
                new Titan(750, 75, 25, 50, 50, null)));
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

    // EFFECTS: creates a new instance of AttackOnTitanApp which runs the game
    public static void main(String[] args) {
        new AttackOnTitanApp();
    }
}
