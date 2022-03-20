package model.buildings;

import model.GameState;
import model.buildings.Building;
import org.json.JSONObject;
import persistence.Writable;

public class TownHouse extends Building implements Writable {
    private static final int WIDTH = 200;
    private static final int HEIGHT = 200;
    private int rotation;

    public static final int DEFAULT_HP = 120;

    public TownHouse(int x, int y, int rotation, GameState gs) {
        super(x, y, WIDTH, HEIGHT, DEFAULT_HP, gs);
        this.rotation = rotation;
    }

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
