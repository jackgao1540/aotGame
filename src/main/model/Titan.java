package model;

// Represents a titan having a name and boolean state indicating whether it is alive.
public class Titan {
    private String name;   // titan name
    private boolean state; // alive or dead
    private int rewardValue; // monetary reward for defeating this titan

    public static final int DEFAULT_REWARD = 50; // the default money for a titan

    public Titan(String s) {
        this.name = s;
        this.state = true;
        this.rewardValue = DEFAULT_REWARD;
    }

    public Titan(String s, int r) {
        this.name = s;
        this.state = true;
        this.rewardValue = r;
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
