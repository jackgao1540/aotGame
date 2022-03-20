package model.titans;

import model.Collidable;
import model.GameState;
import org.json.JSONObject;
import persistence.*;

// Represents a titan having a name and boolean state indicating whether it is alive.
public class Titan extends Collidable implements Writable {
    private int rewardValue; // monetary reward for defeating this titan

    public static final int DEFAULT_REWARD = 50; // the default money for a titan

    // EFFECTS: creates a titan with name s and default values
    public Titan(int r, int x, int y, int w, int h, GameState gs) {
        super(x, y, w, h, gs);
        this.rewardValue = r;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("reward", this.rewardValue);
        json.put("x", this.collidableX);
        json.put("y", this.collidableY);
        json.put("width", this.width);
        json.put("height", this.height);
        return json;
    }

    public int getReward() {
        return this.rewardValue;
    }

    // EFFECTS: returns the reward value as a string
    public String getRewardString() {
        return ((Integer)this.rewardValue).toString();
    }
}
