package model;

//Represents a titan having a name and boolean state indicating whether it is alive.
public class Titan {
    private String name;
    private boolean state;

    public Titan(String s) {
        this.name = s;
        this.state = true;
    }

    public boolean isDead() {
        return !(this.state);
    }

    public void makeDead() {
        this.state = false;
    }
}
