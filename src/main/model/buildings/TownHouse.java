package model.buildings;

import model.GameState;
import model.buildings.Building;
import org.json.JSONObject;
import persistence.Writable;

import java.awt.*;

// Represents a town house building
public class TownHouse extends Building implements Writable {

    private static final int WIDTH = 200;  // default width
    private static final int HEIGHT = 100; // default height
    private int rotation;                  // angle to be rotated

    public static final Color COLOR = new Color(150, 130, 70); // default color

    public static final int DEFAULT_HP = 120; // default hp

    // EFFECTS: creates a townhouse with given x, y, rotation, and gamestate
    public TownHouse(int x, int y, int rotation, GameState gs) {
        super(x, y, WIDTH, HEIGHT, DEFAULT_HP, gs);
        this.rotation = rotation;
    }

    // EFFECTS: loads in a previous townhouse with custom hp
    public TownHouse(int x, int y, int hp, int r, GameState gs) {
        super(x, y, WIDTH, HEIGHT, hp, gs);
        this.rotation = r;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("x", this.collidableX);
        json.put("y", this.collidableY);
        json.put("hitPoints", this.hitPoints);
        json.put("rotation", this.rotation);
        return json;
    }

    public int getRotation() {
        return this.rotation;
    }
}
