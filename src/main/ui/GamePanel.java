package ui;

import model.GameState;
import model.buildings.Shop;
import model.buildings.TownHall;
import model.buildings.TownHouse;
import model.player.Player;
import model.titans.Titan;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

// Represents the game panel
public class GamePanel extends JPanel {

    private AttackOnTitanApp attackOnTitanApp; // the app
    private GameState gs; // the gamestate

    // EFFECTS: creates a gamepanel associated with an AttackOnTitanApp
    public GamePanel(AttackOnTitanApp aotApp) {
        attackOnTitanApp = aotApp;
        setPreferredSize(new Dimension(GameState.WIDTH, GameState.HEIGHT));
        setBackground(new Color(90, 130, 100));
        setVisible(true);
        setLayout(null);
        gs = attackOnTitanApp.getGameState();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        super.paintComponent(g);
        gs.checkGameOver();
        if (gs.isGameOver() && gs.getFirstRoundEndTime() == -69) {
            // game over screen
            g2.setColor(new Color(169, 169, 196));
            g2.fillRect(0, 0, GameState.WIDTH, GameState.HEIGHT);
            if (gs.getTownHall().isDestroyed()) {
                g2.setColor(new Color(100, 0, 0));
            } else {
                g2.setColor(new Color(50, 50, 210));
            }
            g2.fillOval(GameState.WIDTH / 4, GameState.HEIGHT / 4, GameState.WIDTH  / 2, GameState.HEIGHT / 2);
            if (gs.getTownHall().isDestroyed()) {
                g2.setColor(new Color(255, 170, 170));
                g2.drawString("DEFEAT", GameState.WIDTH / 2 - 20, GameState.HEIGHT / 2 - 5);
            } else {
                g2.setColor(new Color(170, 170, 255));
                g2.drawString("VICTORY", GameState.WIDTH / 2 - 20, GameState.HEIGHT / 2 - 5);
            }
        } else {
            drawGame(g);
        }
    }

    // MODIFIES: this
    // EFFECTS: sets the gamestate
    public void setGameState(GameState gs) {
        this.gs = gs;
    }

    // MODIFIES: this
    // EFFECTS: draws the game
    private void drawGame(Graphics g) {
        drawBuildings(g);
        drawTownHall(g);
        drawShop(g);
        drawTitans(g);
        drawPlayer(g);
    }

    // EFFECTS: draws the titans
    public void drawTitans(Graphics g) {
        Color savedCol = g.getColor();
        ArrayList<Titan> arr = gs.getTitans();
        g.setColor(Titan.COLOR);
        for (Titan t : arr) {
            drawTitan(g, t);
        }
        g.setColor(savedCol);
    }

    // EFFECTS: draws a titan
    public void drawTitan(Graphics g, Titan t) {
        Graphics2D g2 = (Graphics2D)g;
        int startX = GameState.WIDTH / 2 - t.getWidth() / 2 + t.getX() - gs.getPlayer().getX();
        int startY = GameState.HEIGHT / 2 - t.getHeight() / 2 + t.getY() - gs.getPlayer().getY();
        g2.fillOval(startX, startY, t.getWidth(), t.getHeight());
        // show health bar
        int x = GameState.WIDTH / 2 - t.getWidth() / 2 + t.getX() - gs.getPlayer().getX();
        int w = t.getWidth();
        int y = GameState.HEIGHT / 2 + t.getHeight() / 2 + t.getY() - gs.getPlayer().getY();
        int h = t.getHeight() / 10;
        Color savedCol = g.getColor();
        g.setColor(new Color(0, 255, 0));
        int percent = w * t.getHealth() / Titan.DEFAULT_HP;
        g.fillRect(x, y, percent, h);
        g.setColor(new Color(255, 0, 0));
        g.fillRect(x + percent, y, w - percent, h);
        g.setColor(savedCol);
    }

    // EFFECTS: draws the player
    public void drawPlayer(Graphics g) {
        Color savedCol = g.getColor();
        Player p = gs.getPlayer();
        Graphics2D g2 = (Graphics2D)g;
        g2.setColor(Player.COLOR);
        // player is in center of the screen
        g2.fillOval(GameState.WIDTH / 2 - p.WIDTH / 2, GameState.HEIGHT / 2 - p.HEIGHT / 2, p.WIDTH, p.HEIGHT);
        g.setColor(savedCol);
    }

    // EFFECTS: draws the buidlings
    public void drawBuildings(Graphics g) {
        Color savedCol = g.getColor();
        ArrayList<TownHouse> b = gs.getBuildings();
        g.setColor(TownHouse.COLOR);
        for (TownHouse t : b) {
            drawTownHouse(g, t);
        }
        g.setColor(savedCol);
    }

    // EFFECTS: draws the townhall
    public void drawTownHall(Graphics g) {
        Color savedCol = g.getColor();
        TownHall t = gs.getTownHall();
        Graphics2D g2d = (Graphics2D)g;
        int x = GameState.WIDTH / 2 - t.getWidth() / 2 + t.getX() - gs.getPlayer().getX();
        int y = GameState.HEIGHT / 2 - t.getHeight() / 2 + t.getY() - gs.getPlayer().getY();
        Rectangle rect2 = new Rectangle(x, y, t.getWidth(), t.getHeight());
        g2d.draw(rect2);
        g2d.fill(rect2);
        g.setColor(new Color(0, 255, 0));
        int percent = t.getWidth() * t.getHitPoints() / t.DEFAULT_HP;
        g.fillRect(x, y + t.getHeight() + 10, percent, 8);
        g.setColor(new Color(255, 0, 0));
        g.fillRect(x + percent, y + t.getHeight() + 10, t.getWidth() - percent, 8);
        g.setColor(savedCol);
    }

    // EFFECTS: draws the shop
    public void drawShop(Graphics g) {
        Color savedCol = g.getColor();
        Shop s = gs.getShop();
        Graphics2D g2d = (Graphics2D)g;
        int x = GameState.WIDTH / 2 - s.getWidth() / 2 + s.getX() - gs.getPlayer().getX();
        int y = GameState.HEIGHT / 2 - s.getHeight() / 2 + s.getY() - gs.getPlayer().getY();
        Rectangle rect2 = new Rectangle(x, y, s.getWidth(), s.getHeight());
        g2d.draw(rect2);
        g2d.fill(rect2);
        g.setColor(savedCol);
    }

    // EFFECTS: draws a townhouse
    public void drawTownHouse(Graphics g, TownHouse t) {
        int originalX1 = t.getX();
        int originalY1 = t.getY();
        Graphics2D g2d = (Graphics2D)g;
        int x1 = GameState.WIDTH / 2 - t.getWidth() / 2 + originalX1 - gs.getPlayer().getX();
        int y1 = GameState.HEIGHT / 2 - t.getHeight() / 2 + originalY1 - gs.getPlayer().getY();
        Rectangle rect2 = new Rectangle(x1, y1, t.getWidth(), t.getHeight());
        int x2 = GameState.WIDTH / 2 + originalX1 - gs.getPlayer().getX();
        int y2 = GameState.HEIGHT / 2 + originalY1 - gs.getPlayer().getY();
        g2d.rotate(Math.toRadians(t.getRotation()), x2, y2);
        g2d.draw(rect2);
        g2d.fill(rect2);
        g2d.rotate(-Math.toRadians(t.getRotation()), x2, y2);
    }
}
