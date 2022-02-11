package model;

import java.util.ArrayList;

//Represents a Player having items, money, and a name.
public class Player {
    private String name;
    private int money;
    private ArrayList<Item> items;

    public static final int DEFAULT_MONEY = 100;

    public Player(String n) {
        this.name = n;
        this.money = DEFAULT_MONEY;
        this.items = new ArrayList<Item>();
    }

    public void addItem(Item i) {
        items.add(i);
    }

    public void makePurchase(int price, Item i) {
        this.money -= price;
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
