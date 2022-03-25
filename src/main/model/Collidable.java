package model;

// Represents an object that can be collided with
public abstract class Collidable {

    protected int collidableX; // the object's x postiion
    protected int collidableY; // the object's y postiion
    protected int sectorX; // the sector row of the object
    protected int sectorY; // the sector column of the object
    protected int width;  // width
    protected int height; // height
    protected GameState gs; // gamestate

    // EFFECTS: creates a collidable with x, y, widht, height, and gamestate
    public Collidable(int x, int y, int w, int h, GameState gs) {
        this.sectorX = -1;
        this.sectorY = -1;
        this.collidableX = x;
        this.collidableY = y;
        this.width = w;
        this.height = h;
        this.gs = gs;
    }

    // MODIFIES: this, gs.sectors
    // EFFECTS: places this object in a sector to help with reducing work calculating collisions
    public void setSector() {
        if (sectorX != -1 && sectorY != -1) {
            gs.getSector(sectorX, sectorY).remove(this);
        }
        this.sectorX = collidableX / gs.SECTOR_WIDTH;
        this.sectorY = collidableY / gs.SECTOR_HEIGHT;
        gs.getSector(sectorX, sectorY).add(this);
    }

    // MODIFIES: this
    // EFFECTS: sets the gamestate for this object, then sets the sector
    public void setGameState(GameState gs) {
        this.gs = gs;
        setSector();
    }

    public int getX() {
        return this.collidableX;
    }

    public int getY() {
        return this.collidableY;
    }

    public int getSectorX() {
        return this.sectorX;
    }

    public int getSectorY() {
        return this.sectorY;
    }

    // MODIFIES: this
    // EFFECTS: changes x
    public void updateX(int delta) {
        this.collidableX += delta;
    }

    // MODIFIES: this
    // EFFECTS: changes y
    public void updateY(int delta) {
        this.collidableY += delta;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }
}
