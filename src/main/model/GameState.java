package model;

import java.util.*;

import org.json.*;
import persistence.*;

public class GameState implements Writable {
    private Player player;
    private ArrayList<Titan> titans;

    // MODIFIES: this
    // EFFECTS: creates a gamestate with the given player and titans list
    public GameState(Player p, ArrayList<Titan> t) {
        this.player = p;
        this.titans = t;
    }

    public Player getPlayer() {
        return this.player;
    }

    public ArrayList<Titan> getTitans() {
        return this.titans;
    }

    // MODIFIES: this
    // EFFECTS: adds a titan to the list
    public void addTitan(Titan t) {
        this.titans.add(t);
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("player", this.player.toJson());
        json.put("titans", titansToJson());
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
