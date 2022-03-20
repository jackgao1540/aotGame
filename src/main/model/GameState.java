package model;

import java.util.*;

import model.buildings.Building;
import model.buildings.Shop;
import model.buildings.TownHall;
import model.buildings.TownHouse;
import model.player.Player;
import model.titans.Titan;
import org.json.*;
import persistence.*;

public class GameState implements Writable {
    public static final int WIDTH = 8000;
    public static final int HEIGHT = 6000;
    public static final int SECTOR_ROWS = 10;
    public static final int SECTOR_COLS = 10;
    public static final int SECTOR_WIDTH = WIDTH / SECTOR_ROWS;
    public static final int SECTOR_HEIGHT = HEIGHT / SECTOR_COLS;

    private ArrayList<ArrayList<ArrayList<Collidable>>> sectors;
    private Player player;
    private ArrayList<Titan> titans;
    private ArrayList<TownHouse> buildings;
    private TownHall townHall;
    private Shop shop;

    // MODIFIES: this
    // EFFECTS: creates a gamestate with the given player, and arrays of titans and buildings
    public GameState(Player p, ArrayList<Titan> t, ArrayList<TownHouse> b, TownHall th, Shop s) {
        this.player = p;
        this.titans = t;
        this.buildings = b;
        this.sectors = new ArrayList<ArrayList<ArrayList<Collidable>>>();
        ArrayList<Collidable> arr =  new ArrayList<Collidable>();
        ArrayList<ArrayList<Collidable>> arr2 = new ArrayList<ArrayList<Collidable>>();
        for (int i = 0; i < SECTOR_COLS; i++) {
            arr2.add(arr);
        }
        for (int i = 0; i < SECTOR_ROWS; i++) {
            sectors.add(arr2);
        }
        this.shop = s;
        this.townHall = th;
    }

    public ArrayList<Collidable> getSector(int x, int y) {
        return sectors.get(x).get(y);
    }

    public void removeBuilding(Building b) {
        buildings.remove(b);
    }

    public Player getPlayer() {
        return this.player;
    }

    public ArrayList<Titan> getTitans() {
        return this.titans;
    }

    public ArrayList<TownHouse> getBuildings() {
        return this.buildings;
    }

    public TownHall getTownHall() {
        return this.townHall;
    }

    public Shop getShop() {
        return this.shop;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("player", this.player.toJson());
        json.put("titans", titansToJson());
        json.put("buildings", buildingsToJson());
        json.put("TownHall", townHall.toJson());
        json.put("Shop", shop.toJson());
        return json;
    }

    private JSONArray buildingsToJson() {
        JSONArray json = new JSONArray();
        for (TownHouse t : this.buildings) {
            json.put(t.toJson());
        }
        return json;
    }

    // EFFECTS: converts the titans to a JSONArray
    private JSONArray titansToJson() {
        JSONArray json = new JSONArray();
        for (Titan t : this.titans) {
            json.put(t.toJson());
        }
        return json;
    }

}
