package ui;

import model.*;
import model.Item;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

// Represetns the shop window for player to buy items
public class ShopWindow extends JFrame {

    private GameState gs;           // gamestate
    private ArrayList<Item> items;  // list of items
    private JPanel contentPane;     // content panel
    private JScrollPane scrollPane1;// scrollpane 1
    private JScrollPane scrollPane2;// scrollpane 2
    private JTextArea textArea1;    // text area 1
    private JTextArea textArea2;    // text area 2
    private JButton submit;         // submit button
    private JTextField textField;   // submit textfield

    // EFFECTS: creates a shop window with gs and list of items
    public ShopWindow(GameState gs, ArrayList<Item> i) {
        super("shop");
        this.gs = gs;
        items = i;
        init1();
        pack();
        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: initializes the contentpane
    private void init1() {
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));
        setContentPane(contentPane);
        scrollPane1 = new JScrollPane();
        scrollPane1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        contentPane.add(scrollPane1);
        scrollPane1.setPreferredSize(new Dimension(250, 500));
        textArea1 = new JTextArea(10, 10);
        scrollPane1.setViewportView(textArea1);
        textArea1.append("$$$ SHOP ---- ITEMS $$$" + "\n");
        for (Item item : items) {
            textArea1.append(item.getName() + ": $ " + item.getPrice() + "\n");
        }
        init2();
    }

    // MODIFIES: this
    // EFFECTS: initializes the contentpane part 2
    private void init2() {
        scrollPane2 = new JScrollPane();
        scrollPane2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        contentPane.add(scrollPane2);
        scrollPane2.setPreferredSize(new Dimension(250, 500));
        textArea2 = new JTextArea(10, 10);
        scrollPane2.setViewportView(textArea2);
        textArea2.append("XXX PLAYER <==> ITEMS XXX\n");
        textArea2.append("Player Money: $" + gs.getPlayer().getMoney() + "\n");
        textArea2.append("Your ATTACK: " + gs.getPlayer().getAttack() + "\n");
        for (Item item : gs.getPlayer().getItems()) {
            textArea2.append(item.getName() + "\n");
        }
        textField = new JTextField(25);
        submit = new JButton("SUBMIT");
        submit.setPreferredSize(new Dimension(100, 50));
        contentPane.add(textField);
        contentPane.add(submit);
        submit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                String textFieldValue = textField.getText();
                handleInput(textFieldValue);
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: handles the textfield input
    public void handleInput(String s) {
        if (s.equalsIgnoreCase("quit") || s.equalsIgnoreCase("exit")) {
            this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        }
        boolean found = false;
        // Check if player is selling an item
        for (Item i: gs.getPlayer().getItems()) {
            if (i.getName().equalsIgnoreCase(s)) {
                // sell the item
                textField.setText("Successfully sold item!");
                gs.getPlayer().sellItem(i);
                return;
            }
        }
        // Check if player is buying an item
        for (Item i : items) {
            if (s.equalsIgnoreCase(i.getName())) {
                found = true;
                // try to purchase
                if (gs.getPlayer().getMoney() >= i.getPrice()) {
                    textField.setText("Purchase successful!");
                    gs.getPlayer().makePurchase(i);
                } else {
                    textField.setText("You need a little more money to buy that.");
                }
            }
        }
        textField.setText((!found) ? "Enter a valid item name." : textField.getText());
    }
}
