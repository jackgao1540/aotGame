package model.buildings;

import model.Collidable;
import model.GameState;

public abstract class Building extends Collidable {
    protected int hitPoints;

    public Building(int x, int y, int w, int h, int hp, GameState gs) {
        super(x, y, w, h, gs);
        this.hitPoints = hp;
    }

    public void subtractHP(int hp) {
        this.hitPoints -= hp;
    }

    @Override
    public void setSector() {
        if (sectorX != -1 && sectorY != -1) {
            gs.getSector(sectorX, sectorY).remove(this);
        }
        this.sectorX = collidableX / gs.SECTOR_WIDTH;
        this.sectorY = collidableY / gs.SECTOR_HEIGHT;
        gs.getSector(sectorX, sectorY).add(this);
        //account for overlaps
        if (collidableX - sectorX * gs.SECTOR_WIDTH <= this.width / 2 && sectorX > 0) {
            gs.getSector(sectorX - 1, sectorY).add(this);
        }
        if (collidableY - sectorY * gs.SECTOR_HEIGHT <= this.height / 2 && sectorY > 0) {
            gs.getSector(sectorX, sectorY - 1).add(this);
        }
        if ((sectorX + 1) * gs.SECTOR_WIDTH - collidableX <= this.width / 2 && sectorX < gs.SECTOR_ROWS - 1) {
            gs.getSector(sectorX + 1, sectorY).add(this);
        }
        if ((sectorY + 1) * gs.SECTOR_HEIGHT - collidableY <= this.height / 2 && sectorY < gs.SECTOR_COLS - 1) {
            gs.getSector(sectorX, sectorY + 1).add(this);
        }
    }

    public int getHitPoints() {
        return this.hitPoints;
    }

    public boolean isDestroyed() {
        return this.getHitPoints() <= 0;
    }
}
