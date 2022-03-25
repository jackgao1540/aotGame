package model.buildings;

import model.GameState;
import model.buildings.Building;
import org.json.JSONObject;
import persistence.Writable;

import java.awt.*;

// Represents a town hall building
public class TownHall extends Building implements Writable {

    private static final int WIDTH = 200;    // default width
    private static final int HEIGHT = 200;   // default height

    public static final int DEFAULT_X = GameState.WIDTH / 2;       // default x
    public static final int DEFAULT_Y = GameState.HEIGHT / 2;      // default y
    public static final int DEFAULT_HP = 600;                      // default hp
    public static final Color COLOR = new Color(170, 170, 170);  // default color

    // EFFECTS: creates a new town hall
    public TownHall(GameState gs) {
        super(DEFAULT_X, DEFAULT_Y, WIDTH, HEIGHT, DEFAULT_HP, gs);
    }

    // EFFECTS: loads an old town hall
    public TownHall(int hp, GameState gs) {
        super(DEFAULT_X, DEFAULT_Y, WIDTH, HEIGHT, hp, gs);
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("hitPoints", this.hitPoints);
        return json;
    }
}
