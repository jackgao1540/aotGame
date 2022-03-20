package model;

public class Collidable {
    protected final int collidableX;
    protected final int collidableY;
    protected int sectorX;
    protected int sectorY;
    protected int width;
    protected int height;
    protected GameState gs;

    public Collidable(int x, int y, int w, int h, GameState gs) {
        this.sectorX = -1;
        this.sectorY = -1;
        this.collidableX = x;
        this.collidableY = y;
        this.width = w;
        this.height = h;
        this.gs = gs;
    }

    public void setSector() {
        if (sectorX != -1 && sectorY != -1) {
            gs.getSector(sectorX, sectorY).remove(this);
        }
        this.sectorX = collidableX / gs.SECTOR_WIDTH;
        this.sectorY = collidableY / gs.SECTOR_HEIGHT;
        gs.getSector(sectorX, sectorY).add(this);
    }

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

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }
}
