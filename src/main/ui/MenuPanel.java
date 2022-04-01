package ui;

import model.GameState;

import javax.swing.*;
import java.awt.*;

// Represnets a menu panel
public class MenuPanel extends JPanel {
    private JButton newGameButton;      // new game button
    private JButton loadGameButton;     // load game button
    private JButton shopButton;         // shop button
    private JButton saveGameButton;     // save and quit button
    private AttackOnTitanApp attackOnTitanApp; // aotApp

    public static final int SHIFT = 110; // margin/spacing
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
        saveGameButton = new JButton("SAVE GAME");
        saveGameButton.addActionListener(attackOnTitanApp);
        saveGameButton.setActionCommand("SaveGame");
        loadGameButton = new JButton("LOAD GAME");
        loadGameButton.addActionListener(attackOnTitanApp);
        loadGameButton.setActionCommand("LoadGame");
        addButtons2();
    }

    // MODIFES: this
    // EFFECTS: adds buttons pt 2
    private void addButtons2() {
        add(newGameButton);
        add(loadGameButton);
        add(shopButton);
        add(saveGameButton);
        setLayout(null);
        newGameButton.setBounds(SHIFT, 70, WIDTH, HEIGHT);
        loadGameButton.setBounds(3 * SHIFT + WIDTH, 70, WIDTH, HEIGHT);
        shopButton.setBounds(5 * SHIFT + 2 * WIDTH, 70, WIDTH, HEIGHT);
        saveGameButton.setBounds(7 * SHIFT + 3 * WIDTH, 70, WIDTH, HEIGHT);
        newGameButton.setVisible(true);
        loadGameButton.setVisible(true);
        shopButton.setVisible(true);
        saveGameButton.setVisible(true);
    }

}

