package model;

// Represents a collidable object that also moves
public abstract class Movable extends Collidable {
    protected int vx; // x velocity
    protected int vy; // y velocity

    public static final int DEFAULT_VX = 0; // default vx
    public static final int DEFAULT_VY = 0; // default vy

    // EFFECTS: creates a movable object with x, y, widht, height, gamestate
    public Movable(int x, int y, int w, int h, GameState gs) {
        super(x, y, w, h, gs);
        this.vx = DEFAULT_VX;
        this.vy = DEFAULT_VY;
    }

    // MODIFIES: this
    // EFFECTS: updates the object's position
    public void move() {
        updateX(vx);
        updateY(vy);
    }

    // MODIFIES: this
    // EFFECTS: accelerates/decelerates vx
    public void updateVx(int delta) {
        vx += delta;
    }

    // MODIFIES: this
    // EFFECTS: accelerates/decelerates vy
    public void updateVy(int delta) {
        vy += delta;
    }
}
