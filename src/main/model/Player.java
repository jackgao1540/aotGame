package model;

import java.util.ArrayList;

// Represents a Player having items, money, and a name.
public class Player {
    private String name;           // player's name
    private int money;             // player's money
    private ArrayList<Item> items; // player's inventory

    public static final int DEFAULT_MONEY = 100; // default amount of money for player
    public static final Integer MAX_CHARS = 25;     // Maximum length of player name
    public static final Integer MIN_CHARS = 3;      // Minimum length of player name

    // MODIFIES: this
    // EFFECTS: initializes a player with the given name, default money, and no items.
    public Player(String n) {
        this.name = n;
        this.money = DEFAULT_MONEY;
        this.items = new ArrayList<Item>();
    }

    // MODIFIES: this
    // EFFECTS: adds an item to the player's inventory
    public void addItem(Item i) {
        items.add(i);
    }

    // MODIFIES: this
    // EFFECTS: deducts from the player's money and adds an item
    public void makePurchase(Item i) {
        this.money -= i.getPrice();
        addItem(i);
    }

    public ArrayList<Item> getItems() {
        return this.items;
    }

    public String getName() {
        return this.name;
    }

    public int getMoney() {
        return this.money;
    }

    // EFFECTS: returns the player's money as a string to be displayed
    public String getMoneyString() {
        return ((Integer)this.money).toString();
    }

    // EFFECTS: checks if a given name is valid
    public boolean validName(String s) {
        if (s.length() > MAX_CHARS || s.length() < MIN_CHARS) {
            return false;
        }
        if (s.contains(" ")) {
            return false;
        }
        return true;
    }

    // REQUIRES: diff >= 0
    // MODIFIES: this
    // EFFECTS: adds set amount of money to player's inventory
    public void addMoney(int diff) {
        this.money += diff;
    }
}
