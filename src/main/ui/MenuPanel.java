package ui;

import model.GameState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class MenuPanel extends JPanel {
    private JButton newGameButton;
    private JButton loadGameButton;
    private JButton quitButton;
    private AttackOnTitanApp attackOnTitanApp;

    public static final int SHIFT = 250;
    public static final int WIDTH = 400;
    public static final int HEIGHT = 200;

    public MenuPanel(AttackOnTitanApp aotApp) {
        attackOnTitanApp = aotApp;
        setPreferredSize(new Dimension(GameState.WIDTH, GameState.HEIGHT));
        setBackground(new Color(169, 169, 196));
        newGameButton = new JButton("NEW GAME");
        newGameButton.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        loadGameButton = new JButton("LOAD GAME");
        loadGameButton.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        quitButton = new JButton("QUIT");
        quitButton.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        add(newGameButton);
        add(loadGameButton);
        add(quitButton);
        setLayout(null);
        newGameButton.setBounds(GameState.WIDTH / 2 - WIDTH / 2, GameState.HEIGHT / 2  - HEIGHT / 2 - SHIFT, WIDTH, HEIGHT);
        loadGameButton.setBounds(GameState.WIDTH / 2 - WIDTH / 2, GameState.HEIGHT / 2  - HEIGHT / 2, WIDTH, HEIGHT);
        quitButton.setBounds(GameState.WIDTH / 2 - WIDTH / 2, GameState.HEIGHT / 2  - HEIGHT / 2 + SHIFT, WIDTH, HEIGHT);
        newGameButton.setVisible(true);
        loadGameButton.setVisible(true);
        quitButton.setVisible(true);
        setVisible(true);
    }

    private class NewGameAction extends AbstractAction {

        NewGameAction() {
            super("New Game");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            AlarmCode alarmCode = new AlarmCode(kp.getCode());
            kp.clearCode();
            try {
                ac.addCode(alarmCode);
            } catch (NotValidCodeException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "System Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
