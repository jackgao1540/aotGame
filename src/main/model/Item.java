package model;

import org.json.JSONObject;
import persistence.*;

// Represents an item for the player to hold, having a name and a price.
public class Item implements Writable {
    private String name;  // item name
    private int price;    // item price

    public Item(String n, int p) {
        this.name = n;
        this.price = p;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name",  this.name);
        json.put("price", this.price);
        return json;
    }

    public int getPrice() {
        return this.price;
    }

    public String getName() {
        return this.name;
    }
}
