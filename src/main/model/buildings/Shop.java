package model.buildings;

import model.GameState;
import model.buildings.Building;
import org.json.JSONObject;
import persistence.Writable;

public class Shop extends Building implements Writable {
    private static final int WIDTH = 170;
    private static final int HEIGHT = 100;

    public static final int DEFAULT_X = GameState.WIDTH / 2 - WIDTH / 2;
    public static final int DEFAULT_Y = GameState.HEIGHT - HEIGHT / 2;
    public static final int DEFAULT_HP = 480;

    public Shop(GameState gs) {
        super(DEFAULT_X, DEFAULT_Y, WIDTH, HEIGHT, DEFAULT_HP, gs);
    }

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
