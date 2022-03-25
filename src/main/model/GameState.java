package model;

import java.awt.event.KeyEvent;
import java.util.*;

import model.buildings.*;
import model.player.*;
import model.titans.*;
import org.json.*;
import persistence.*;
import ui.AttackOnTitanApp;

// Represents the state of the game
public class GameState implements Writable {

    public static final int WIDTH = 1600;                        // width
    public static final int HEIGHT = 900;                        // height
    public static final int SECTOR_ROWS = 10;                    // sector rows
    public static final int SECTOR_COLS = 10;                    // sector cols
    public static final int SECTOR_WIDTH = WIDTH / SECTOR_ROWS;  // sector width
    public static final int SECTOR_HEIGHT = HEIGHT / SECTOR_COLS;// sector height

    private boolean isGameOver;                                  // if game is over
    private ArrayList<ArrayList<ArrayList<Collidable>>> sectors; // sectors
    private Player player;                                       // teh player of the game
    private ArrayList<Titan> titans;                             // titans
    private ArrayList<TownHouse> buildings;                      // buildings
    private TownHall townHall;                                   // the townhall
    private Shop shop;                                           // the shop
    private AttackOnTitanApp attackOnTitanApp;                   // the app
    private long firstRoundEndTime;                               // the time that first round ended

    public static final int ROUND_INTERVAL = 20000;              // time between rounds

    // MODIFIES: this
    // EFFECTS: creates a gamestate with the given player, and arrays of titans and buildings
    public GameState(Player p, ArrayList<Titan> t, ArrayList<TownHouse> b, TownHall th, Shop s, AttackOnTitanApp app) {
        this.player = p;
        firstRoundEndTime = -1;
        this.titans = t;
        this.buildings = b;
        this.isGameOver = false;
        this.attackOnTitanApp = app;
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

    public long getFirstRoundEndTime() {
        return firstRoundEndTime;
    }

    // MODIFIES: this
    // EFFECTS: updates the game state
    public void update(boolean w, boolean a, boolean s, boolean d) {
        player.update((s ? 1 : 0) - (w ? 1 : 0), (d ? 1 : 0) - (a ? 1 : 0));
        ArrayList<Titan> toRemove = new ArrayList<>();
        for (Titan t : titans) {
            t.update();
            if (t.getHealth() <= 0) {
                toRemove.add(t);
            }
        }
        for (Titan t : toRemove) {
            if (titans.contains(t)) {
                titans.remove(t);
                player.addMoney(t.getReward());
            }
        }
        if (System.currentTimeMillis() - firstRoundEndTime >= ROUND_INTERVAL && firstRoundEndTime > 0) {
            isGameOver = false;
            titans = new ArrayList<>(Arrays.asList(new Titan(1000, 100, 300, 50, 69, this),
                    new Titan(1000, 100, 600, 50, 69, this),
                    new Titan(1000, 400, 850, 50, 69, this),
                    new Titan(750, 400, 50, 50, 69, this)));
            firstRoundEndTime = -69;
        }
        checkGameOver();
    }

    // EFFECTS: checks if the game is over
    public void checkGameOver() {
        // if townhall is destroyed then game is donezo
        if (attackOnTitanApp.getTownHall().isDestroyed()) {
            isGameOver = true;
        }
        if (titans.size() == 0) {
            isGameOver = true;
        }
        if (isGameOver) {
            titans.clear();
            if (firstRoundEndTime == -1 && !attackOnTitanApp.getTownHall().isDestroyed()) {
                firstRoundEndTime = System.currentTimeMillis();
            } else if (!attackOnTitanApp.getTownHall().isDestroyed()) {
                // wait for seocnd round
            } else {
                firstRoundEndTime = -69;
            }
        }
    }

    // EFFECTS: returns a list of collidables at a given sector
    public ArrayList<Collidable> getSector(int x, int y) {
        return sectors.get(x).get(y);
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

    // EFFECTS: returns true if game is over
    public boolean isGameOver() {
        return isGameOver;
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

    // EFFECTS: converts the buildings array to JSONArray
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
