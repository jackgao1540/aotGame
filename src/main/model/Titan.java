package model;

// Represents a titan having a name and boolean state indicating whether it is alive.
public class Titan {
    private String name;   // titan name
    private boolean state; // alive or dead

    public Titan(String s) {
        this.name = s;
        this.state = true;
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
