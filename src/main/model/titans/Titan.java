package model.titans;

import model.Collidable;
import model.GameState;
import model.buildings.TownHall;
import org.json.JSONObject;
import persistence.*;

import java.awt.*;

// Represents a titan having a name and boolean state indicating whether it is alive.
public class Titan extends Collidable implements Writable {
    private int rewardValue; // monetary reward for defeating this titan
    private long lastAction; // time that last action was taken
    private boolean actionType; // true for move, false for attacking the townhall
    private int hp;     // titans current hp

    public static final int DEFAULT_REWARD = 50; // the default money for a titan
    public static final Color COLOR = new Color(130, 60, 60); // default color
    public static final int DECISION_INTERVAL = 4000; // time to make decision
    public static final int ATTACK_DMG = 100; // attack stat
    public static final int SPEED = 2;  // default speed
    public static final int DEFAULT_HP = 120; // default hp

    // EFFECTS: creates a titan with name s and default values
    public Titan(int r, int x, int y, int w, int h, GameState gs) {
        super(x, y, w, h, gs);
        this.rewardValue = r;
        actionType = true;
        lastAction = -DECISION_INTERVAL - 1;
        this.hp = DEFAULT_HP;
    }

    // EFFECTS: creates a titan with everything given
    public Titan(int r, int x, int y, int w, int h, int hp, GameState gs) {
        super(x, y, w, h, gs);
        this.rewardValue = r;
        actionType = true;
        lastAction = -DECISION_INTERVAL - 1;
        this.hp = hp;
    }

    // MODIFIES: this, gs.townHall
    // EFFECTS: causes titan to move or attack town hall
    public void update() {
        if (hitsTownHall()) {
            if (System.currentTimeMillis() - lastAction >= DECISION_INTERVAL) {
                lastAction = System.currentTimeMillis();
                // attack the town hall
                gs.getTownHall().subtractHP(ATTACK_DMG);
            }
        } else {
            //move towards town hall
            double a = ((double)(gs.getTownHall().getY() - getY()));
            double b = ((double)(gs.getTownHall().getX() - getX()));
            double angle = Math.atan(a / b);
            updateX((int)(((double)SPEED) * Math.cos(angle)));
            updateY((int)(((double)SPEED) * Math.sin(angle)));
        }
    }

    // EFFECTS: checks if titan is in attack range of town hall
    public boolean hitsTownHall() {
        TownHall t = gs.getTownHall();
        boolean a = collidableX >= t.getX() - t.getWidth() / 2 - getWidth() / 2;
        boolean b = collidableX <= t.getX() + t.getWidth() / 2 + getWidth() / 2;
        boolean c = collidableY >= t.getY() - t.getHeight() / 2 - getHeight() / 2;
        boolean d = collidableY <= t.getY() + t.getHeight() / 2 + getHeight() / 2;
        return a && b && c && d;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("reward", this.rewardValue);
        json.put("x", this.collidableX);
        json.put("y", this.collidableY);
        json.put("width", this.width);
        json.put("height", this.height);
        json.put("hp", this.hp);
        return json;
    }

    public int getReward() {
        return this.rewardValue;
    }

    // MODIFIES: this
    // EFEFCTS: subtracts from hp
    public void subtractHP(int dmg) {
        hp -= dmg;
    }

    public int getHealth() {
        return hp;
    }
}
