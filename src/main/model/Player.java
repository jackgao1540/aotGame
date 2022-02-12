package model;

import java.util.ArrayList;

// Represents a Player having items, money, and a name.
public class Player {
    private String name;           // player's name
    private int money;             // player's money
    private ArrayList<Item> items; // player's inventory

    public static final int DEFAULT_MONEY = 100; // default amount of money for player

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
}
