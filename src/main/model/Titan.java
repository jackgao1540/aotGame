package model;

import org.json.JSONObject;
import persistence.*;

// Represents a titan having a name and boolean state indicating whether it is alive.
public class Titan implements Writable {
    private String name;   // titan name
    private boolean state; // alive or dead
    private int rewardValue; // monetary reward for defeating this titan

    public static final int DEFAULT_REWARD = 50; // the default money for a titan

    // EFFECTS: creates a titan with name s and default values
    public Titan(String s) {
        this.name = s;
        this.state = true;
        this.rewardValue = DEFAULT_REWARD;
    }

    //EFFECTS: creates a titan with given name and reward
    public Titan(String s, int r) {
        this.name = s;
        this.state = true;
        this.rewardValue = r;
    }

    // EFFECTS: creates a titan with everything given
    public Titan(String s, boolean st, int r) {
        this.name = s;
        this.state = st;
        this.rewardValue = r;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", this.name);
        json.put("state", this.state);
        json.put("reward", this.rewardValue);
        return json;
    }

    public int getReward() {
        return this.rewardValue;
    }

    // EFFECTS: returns the reward value as a string
    public String getRewardString() {
        return ((Integer)this.rewardValue).toString();
    }

    public String getName() {
        return this.name;
    }

    // EFFECTS: returns if titan is dead
    public boolean isDead() {
        return !(this.state);
    }

    // MODIFIES: this
    // EFFECTS: makes the titan dead
    public void makeDead() {
        this.state = false;
    }
}
