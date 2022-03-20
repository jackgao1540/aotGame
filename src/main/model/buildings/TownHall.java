package model.buildings;

import model.GameState;
import model.buildings.Building;
import org.json.JSONObject;
import persistence.Writable;

public class TownHall extends Building implements Writable {
    private static final int WIDTH = 200;
    private static final int HEIGHT = 200;

    public static final int DEFAULT_X = GameState.WIDTH / 2 - WIDTH / 2;
    public static final int DEFAULT_Y = GameState.HEIGHT / 2 - HEIGHT / 2;
    public static final int DEFAULT_HP = 600;

    public TownHall(GameState gs) {
        super(DEFAULT_X, DEFAULT_Y, WIDTH, HEIGHT, DEFAULT_HP, gs);
    }

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
