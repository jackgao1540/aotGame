package model.buildings;

import model.GameState;
import model.buildings.Building;
import org.json.JSONObject;
import persistence.Writable;

import java.awt.*;

// Represents a shop building
public class Shop extends Building implements Writable {

    private static final int WIDTH = 170;  // shop width
    private static final int HEIGHT = 100; // shop height

    public static final int DEFAULT_X = GameState.WIDTH / 2;           // default x coordinate
    public static final int DEFAULT_Y = GameState.HEIGHT - HEIGHT / 2; // default y coordinate
    public static final int DEFAULT_HP = 480;                          // default hitpoints
    public static final Color COLOR = new Color(100, 150, 90);// shop color

    // EFFECTS: creates a new shop with a gamestate
    public Shop(GameState gs) {
        super(DEFAULT_X, DEFAULT_Y, WIDTH, HEIGHT, DEFAULT_HP, gs);
    }

    // EFFECTS: loads an old shop with custom hp
    public Shop(int hp, GameState gs) {
        super(DEFAULT_X, DEFAULT_Y, WIDTH, HEIGHT, hp, gs);
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("hitPoints", this.hitPoints);
        return json;
    }
}
