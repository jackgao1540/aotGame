package model.player;

import java.awt.*;
import java.util.ArrayList;

import model.Event;
import model.Item;
import model.*;
import model.GameState;
import model.titans.Titan;
import persistence.*;
import org.json.*;

// Represents a Player having items, money, and a name.
public class Player extends Movable implements Writable {
    private String name;           // player's name
    private int money;             // player's money
    private ArrayList<Item> items; // player's inventory
    private int attack;            // player's attack stat

    public static final int DEFAULT_MONEY = 100; // default amount of money for player
    public static final Integer MAX_CHARS = 25;     // Maximum length of player name
    public static final Integer MIN_CHARS = 3;      // Minimum length of player name
    public static final int DEFAULT_X = GameState.WIDTH / 2;  // default x
    public static final int DEFAULT_Y = (GameState.HEIGHT / 2) + 130; // default y
    public static final int MAX_SPEED = 20; // maximum speed
    public static final int DEFAULT_ACCELERATION = 1; // acceleration
    public static final int DEFAULT_ATTACK = 13; // default attack stat
    public static final int WIDTH = 17;  // default width
    public static final int HEIGHT = 23; // default height
    public static final Color COLOR = new Color(255, 0, 0); // default color


    // EFFECTS: initializes a player with the given name, default money, and no items.
    public Player(String n, GameState gs) {
        super(DEFAULT_X, DEFAULT_Y, WIDTH, HEIGHT, gs);
        this.name = n;
        this.attack = DEFAULT_ATTACK;
        this.money = DEFAULT_MONEY;
        this.items = new ArrayList<Item>();
    }

    // EFFECTS: creates a player with everything given
    public Player(String n, int m, ArrayList<Item> i, int x, int y, int attack, GameState gs) {
        super(x, y, WIDTH, HEIGHT, gs);
        this.name = n;
        this.money = m;
        this.items = i;
        this.attack = attack;
    }

    // MODIFIES: this, gs.titans
    // EFFECTS: moves the player and checks for collisions
    public void update(int down, int right) {
        move();
        updateVx(right * DEFAULT_ACCELERATION);
        updateVy(down * DEFAULT_ACCELERATION);
        vx = Math.max(-MAX_SPEED, Math.min(vx, MAX_SPEED));
        vy = Math.max(-MAX_SPEED, Math.min(vy, MAX_SPEED));
        collidableX = Math.max(0, Math.min(collidableX, GameState.WIDTH - 1));
        collidableY = Math.max(0, Math.min(collidableY, GameState.HEIGHT - 1));
        ArrayList<Titan> t = gs.getTitans();
        for (Titan i : t) {
            int a = i.getX() - getX();
            int b = i.getY() - getY();
            if (Math.sqrt(a * a + b * b) < WIDTH / 2 + i.getWidth() / 2) {
                i.subtractHP(attack);
            }
        }
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", this.name);
        json.put("money", this.money);
        json.put("items", itemsToJson());
        json.put("attack", this.attack);
        json.put("x", this.collidableX);
        json.put("y", this.collidableY);
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
        // add the stats
        this.attack += i.getAtk();
    }

    // MODIFIES: this
    // EFFECTS: removes an item from the player's inventory
    public void removeItem(Item i) {
        items.remove(i);
        this.attack -= i.getAtk();
        Event e = new Event("Item " + i.getName() + " sold from inventory for $" + i.getPrice());
        EventLog.getInstance().logEvent(e);
    }

    public int getAttack() {
        return attack;
    }

    // MODIFIES: this
    // EFFECTS: deducts from the player's money and adds an item
    public void makePurchase(Item i) {
        this.money -= i.getPrice();
        addItem(i);
        Event e = new Event("Item " + i.getName() + " purchased for $" + i.getPrice());
        EventLog.getInstance().logEvent(e);
    }

    // MODIFIES: this
    // EFFECTS: adds to the player's money and removes an item
    public void sellItem(Item i) {
        this.money += i.getPrice();
        removeItem(i);
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
    public static boolean validName(String s) {
        if (s.length() > MAX_CHARS || s.length() < MIN_CHARS) {
            return false;
        }
        if (s.contains(" ")) {
            return false;
        }
        return true;
    }

    // MODIFIES: this
    // EFFECTS: adds set amount of money to player's inventory
    public void addMoney(int diff) {
        this.money += diff;
    }
}
