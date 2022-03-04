package model;

import java.util.ArrayList;
import java.util.List;

import persistence.*;
import org.json.*;

// Represents a Player having items, money, and a name.
public class Player implements Writable {
    private String name;           // player's name
    private int money;             // player's money
    private ArrayList<Item> items; // player's inventory

    public static final int DEFAULT_MONEY = 100; // default amount of money for player
    public static final Integer MAX_CHARS = 25;     // Maximum length of player name
    public static final Integer MIN_CHARS = 3;      // Minimum length of player name

    // EFFECTS: initializes a player with the given name, default money, and no items.
    public Player(String n) {
        this.name = n;
        this.money = DEFAULT_MONEY;
        this.items = new ArrayList<Item>();
    }

    // EFFECTS: creates a player with everything given
    public Player(String n, int m, ArrayList<Item> i) {
        this.name = n;
        this.money = m;
        this.items = i;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", this.name);
        json.put("money", this.money);
        json.put("items", itemsToJson());
        return json;
    }

    // EFFECTS: converts the player's items to a JSONArray
    private JSONArray itemsToJson() {
        JSONArray json = new JSONArray();
        for (Item i : items) {
            json.put(i.toJson());
        }
        return json;
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
