package model.player;

import org.json.JSONObject;
import persistence.*;

// Represents an item for the player to hold, having a name and a price.
public class Item implements Writable {
    private String name;  // item name
    private int price;    // item price

    public final int atk;
    public final int spd;
    public final boolean canFire;
    public final int fireDMG;
    public final int projSize;
    public final boolean canDash;
    public final int dashDMG;


    public Item(String n, int p, int atk, int spd, boolean cf, int fd, int ps, boolean cd, int dd) {
        this.name = n;
        this.price = p;
        this.atk = atk;
        this.spd = spd;
        this.canFire = cf;
        this.fireDMG = fd;
        this.projSize = ps;
        this.canDash = cd;
        this.dashDMG = dd;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name",  this.name);
        json.put("price", this.price);
        json.put("atk", this.atk);
        json.put("spd", this.spd);
        json.put("canFire", this.canFire);
        json.put("fireDMG", this.fireDMG);
        json.put("projSize", this.projSize);
        json.put("canDash", this.canDash);
        json.put("dashDMG", this.dashDMG);
        return json;
    }

    public int getPrice() {
        return this.price;
    }

    public String getName() {
        return this.name;
    }

    public int getAtk() {
        return this.atk;
    }

    public int getSpd() {
        return this.spd;
    }

    public int getFireDMG() {
        return this.fireDMG;
    }

    public int getProjSize() {
        return this.projSize;
    }

    public int getDashDMG() {
        return this.dashDMG;
    }
}
