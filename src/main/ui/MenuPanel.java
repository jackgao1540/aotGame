package ui;

import model.GameState;

import javax.swing.*;
import java.awt.*;

// Represnets a menu panel
public class MenuPanel extends JPanel {
    private JButton newGameButton;      // new game button
    private JButton loadGameButton;     // load game button
    private JButton quitButton;         // quit button
    private JButton shopButton;         // shop button
    private JButton saveQuitButton;     // save and quit button
    private AttackOnTitanApp attackOnTitanApp; // aotApp

    public static final int SHIFT = 70; // margin/spacing
    public static final int WIDTH = 180; // width of buttons
    public static final int HEIGHT = 70; // height of buttons

    // EFFECTS: creates a menupanel with an AttackOnTitanApp
    public MenuPanel(AttackOnTitanApp aotApp) {
        attackOnTitanApp = aotApp;
        setPreferredSize(new Dimension(GameState.WIDTH, 200));
        setBackground(new Color(169, 169, 196));
        addButtons1();
        setVisible(true);
    }

    // MODIFES: this
    // EFFECTS: adds buttons
    private void addButtons1() {
        newGameButton = new JButton("NEW GAME");
        newGameButton.addActionListener(attackOnTitanApp);
        newGameButton.setActionCommand("NewGame");
        shopButton = new JButton("SHOP");
        shopButton.addActionListener(attackOnTitanApp);
        shopButton.setActionCommand("Shop");
        saveQuitButton = new JButton("SAVE + QUIT");
        saveQuitButton.addActionListener(attackOnTitanApp);
        saveQuitButton.setActionCommand("SaveQuit");
        loadGameButton = new JButton("LOAD GAME");
        loadGameButton.addActionListener(attackOnTitanApp);
        loadGameButton.setActionCommand("LoadGame");
        quitButton = new JButton("QUIT");
        quitButton.addActionListener(attackOnTitanApp);
        quitButton.setActionCommand("Quit");
        addButtons2();
    }

    // MODIFES: this
    // EFFECTS: adds buttons pt 2
    private void addButtons2() {
        add(newGameButton);
        add(loadGameButton);
        add(quitButton);
        add(shopButton);
        add(saveQuitButton);
        setLayout(null);
        newGameButton.setBounds(SHIFT, SHIFT, WIDTH, HEIGHT);
        loadGameButton.setBounds(3 * SHIFT + WIDTH, SHIFT, WIDTH, HEIGHT);
        quitButton.setBounds(5 * SHIFT + 2 * WIDTH, SHIFT, WIDTH, HEIGHT);
        shopButton.setBounds(7 * SHIFT + 3 * WIDTH, SHIFT, WIDTH, HEIGHT);
        saveQuitButton.setBounds(9 * SHIFT + 4 * WIDTH, SHIFT, WIDTH, HEIGHT);
        newGameButton.setVisible(true);
        loadGameButton.setVisible(true);
        quitButton.setVisible(true);
        shopButton.setVisible(true);
        saveQuitButton.setVisible(true);
    }

}

