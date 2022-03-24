package model;

public abstract class Movable extends Collidable {
    protected int speed;
    protected double theta;
    protected int vx;
    protected int vy;

    public static final int DEFAULT_VX = 0;
    public static final int DEFAULT_VY = 0;

    public Movable(int x, int y, int w, int h, int speed, double theta, GameState gs) {
        super(x, y, w, h, gs);
        this.speed = speed;
        this.theta = theta;
        this.vx = DEFAULT_VX;
        this.vy = DEFAULT_VY;
    }

    public void updateTheta(double theta) {
        this.theta = theta;
    }

    public void update() {
        vx += Math.cos(theta) * speed;
        vy += Math.sin(theta) * speed;
    }

}
