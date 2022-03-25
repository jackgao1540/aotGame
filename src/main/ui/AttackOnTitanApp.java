package ui;

import model.*;
import model.buildings.*;
import model.Item;
import model.player.*;
import model.titans.*;
import persistence.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;

// AttackOnTitanApp, runs the UI for the game
public class AttackOnTitanApp extends JFrame implements ActionListener {
    private static final String JSON_STORE = "./data/game.json";
    private Player player;              // Is the player object for this instance of the game
    private ArrayList<Item> shopItems;  // Is the list of items for the game
    private ArrayList<Titan> titans;    // Is the list of titans
    private ArrayList<TownHouse> buildings; // list of buildings
    private TownHall townHall; // town hall
    private Shop shop; // shop
    private GameState gs;               // Is the GameState (saveable state that represents the game)
    private JsonWriter jsonWriter;      // Object that can write (save) the gamestate
    private JsonReader jsonReader;      // Object that can read the gamestate from JSON
    private int runState;               // 0 for menu, 1 for game (not paused), 2 for paused, 3 for shop screen
    private static final int FPS = 60;  // frames per second
    private static final int INTERVAL = 1000 / FPS; // delay between updates

    //panels
    private boolean started; // if the game has been initialized at least once
    private ShopWindow shopWindow; // the shop frame
    private GamePanel gamePanel;   // panel for video game
    private MenuPanel menuPanel;   // panel for menu
    private static volatile boolean wPressed = false; // if w is pressed
    private static volatile boolean aPressed = false; // if a is pressed
    private static volatile boolean sPressed = false; // if s is pressed
    private static volatile boolean dPressed = false; // if d is pressed
    private static volatile boolean escPressed = false; // if esc is pressed

    // EFFECTS: returns if esc is pressed down
    public static boolean isEscPressed() {
        synchronized (AttackOnTitanApp.class) {
            return escPressed;
        }
    }

    // EFFECTS: returns if w is pressed down
    public static boolean isWPressed() {
        synchronized (AttackOnTitanApp.class) {
            return wPressed;
        }
    }

    // EFFECTS: returns if a is pressed down
    public static boolean isAPressed() {
        synchronized (AttackOnTitanApp.class) {
            return aPressed;
        }
    }

    // EFFECTS: returns if s is pressed down
    public static boolean isSPressed() {
        synchronized (AttackOnTitanApp.class) {
            return sPressed;
        }
    }

    // EFFECTS: returns if d is pressed down
    public static boolean isDPressed() {
        // I am always DPressed
        synchronized (AttackOnTitanApp.class) {
            return dPressed;
        }
    }

    // EFFECTS: runs the initialization of the UI
    public AttackOnTitanApp() {
        super("Attack On Titan");
        started = false;
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE, this);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(false);
        menuPanel = new MenuPanel(this);
        menuPanel.setVisible(true);
        newGameState();
        setGameStates();
        add(menuPanel, BorderLayout.NORTH);
        pack();
        setResizable(false);
        setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((screen.width - getWidth()) / 2, (screen.height - getHeight()) / 2);
        addTimer();
        addKeyEventDispatcher();
    }

    // MODIFIES: this
    // EFFECTS: adds a listener to keypresses
    public void addKeyEventDispatcher() {
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
            @Override
            public boolean dispatchKeyEvent(KeyEvent ke) {
                synchronized (AttackOnTitanApp.class) {
                    switch (ke.getID()) {
                        case KeyEvent.KEY_PRESSED:
                            handleKeyPressed(ke, true);
                            break;
                        case KeyEvent.KEY_RELEASED:
                            handleKeyPressed(ke, false);
                            break;
                    }
                    return false;
                }
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: handles KeyEvents
    private void handleKeyPressed(KeyEvent ke, boolean val) {
        if (ke.getKeyCode() == KeyEvent.VK_W) {
            wPressed = val;
        }
        if (ke.getKeyCode() == KeyEvent.VK_A) {
            aPressed = val;
        }
        if (ke.getKeyCode() == KeyEvent.VK_S) {
            sPressed = val;
        }
        if (ke.getKeyCode() == KeyEvent.VK_D) {
            dPressed = val;
        }
        if (ke.getKeyCode() == KeyEvent.VK_ESCAPE) {
            escPressed = val;
        }
    }

    // REQUIRES: gs != null
    // MODIFIES: this
    // EFFECTS: runs the game LOL
    public void runGame() {
        if (!started) {
            started = true;
            gamePanel = new GamePanel(this);
            add(gamePanel);
            gamePanel.setVisible(true);
        } else {
            gamePanel.setGameState(gs);
        }
    }


    // EFFECTS: creates a timer that updates each INTERVAL milliseconds
    private void addTimer() {
        Timer t = new Timer(INTERVAL, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                gs.update(isWPressed(), isAPressed(), isSPressed(), isDPressed());
                gamePanel.repaint();
            }
        });
        t.start();
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        String action = ae.getActionCommand();
        if (action.equals("NewGame")) {
            // make new game
            newGameState();
            setGameStates();
            runGame();
        } else if (action.equals("LoadGame")) {
            // load game
            loadGameState();
            setGameStates();
            runGame();
        } else if (action.equals("SaveQuit")) {
            saveGameState();
            System.exit(0);
        } else if (action.equals("Shop")) {
            shopWindow = new ShopWindow(gs, shopItems);

        } else {
            //is QUIT
            System.exit(0);
        }
    }

    // MODIFIES: this
    // EFFECTS: creates a new game state and runs game
    public void newGameState() {
        String name = JOptionPane.showInputDialog(this, "What is your name?", null);
        //get player name
        while (!Player.validName(name)) {
            name = JOptionPane.showInputDialog(this, "Enter a valid name.", null);
        }
        //initialize key variables
        player = new Player(name, null);
        initTitans();
        initBuildings();
        townHall = new TownHall(null);
        shop = new Shop(null);
        gs = new GameState(player, titans, buildings, townHall, shop, this);
        initShop();
        runGame();
    }

    public TownHall getTownHall() {
        return townHall;
    }

    public Shop getShop() {
        return shop;
    }

    // MODIFIES: this
    // EFFECTS: sets the game state
    public void setGameStates() {
        player.setGameState(gs);
        for (Titan t: titans) {
            t.setGameState(gs);
        }
        for (TownHouse t: buildings) {
            t.setGameState(gs);
        }
        townHall.setGameState(gs);
        shop.setGameState(gs);
    }

    // EFFECTS: saves the gamestate to file
    private void saveGameState() {
        try {
            jsonWriter.open();
            jsonWriter.write(gs);
            jsonWriter.close();
            System.out.println("Saved game to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads gamestate from file
    private void loadGameState() {
        try {
            gs = jsonReader.read();
            System.out.println("Loaded save from " + JSON_STORE + ", welcome back, " + gs.getPlayer().getName() + "!");
            player = gs.getPlayer();
            titans = gs.getTitans();
            buildings = gs.getBuildings();
            townHall = gs.getTownHall();
            shop = gs.getShop();

            for (TownHouse t : buildings) {
                System.out.println("(" + t.getX() + ", " + t.getY() + ", " + t.getRotation() + ")");
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Could not load game.");
        }
    }

    // MODIFIES: this
    // EFFECTS: creates initial buildings list
    public void initBuildings() {
        buildings = new ArrayList<TownHouse>(Arrays.asList(
                new TownHouse(300, 200, 45, null),
                new TownHouse(800, 250, 0, null),
                new TownHouse(1300, 200, 135, null),
                new TownHouse(100, 450, 0, null),
                new TownHouse(1500, 450, 0, null),
                new TownHouse(300, 700, 135, null),
                new TownHouse(650, 800, 90, null),
                new TownHouse(950, 800, 90, null),
                new TownHouse(1300, 700, 45, null)));
    }

    // MODIFIES: this
    // EFFECTS: initializes the shop with a list of items
    public void initShop() {
        shopItems = new ArrayList<>(Arrays.asList(
                new Item("Rusty Kitchen Knife", 1, 1, 0, false, 0, 0, false, 0),
                new Item("Meat Pie for Sasha", 3, 0, 0, false, 0, 0, false, 0),
                new Item("Zeke's Baseball", 4, 2, 2, true, 5, 2, false, 0),
                new Item("Sword", 10, 10, 0, true, 0, 0, false, 0),
                new Item("ODM Gear", 50, 5, 50, false, 0, 0, true, 100),
                new Item("Thunder Spear",  100, 50, 0, true, 300, 30, false, 0),
                new Item("Mikasa's Red Scarf", 130, 200, 200, false, 0, 0, true, 500),
                new Item("Founding Titan Fluid", 999, 100000, 0, false, 0, 0, false, 0)));
    }

    // MODIFIES: this
    // EFFECTS; initializes the list of titans attacking the village
    public void initTitans() {
        titans = new ArrayList<>(Arrays.asList(
                new Titan(1000, 100, 300, 50, 69, null),
                new Titan(1000, 100, 600, 50, 69, null),
                new Titan(1000, 400, 850, 50, 69, null),
                new Titan(750, 400, 50, 50, 69, null)));
    }

    public GameState getGameState() {
        return gs;
    }

    // EFFECTS: creates a new instance of AttackOnTitanApp which runs the game
    public static void main(String[] args) {
        new AttackOnTitanApp();
    }
}
