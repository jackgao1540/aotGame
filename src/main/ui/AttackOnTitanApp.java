package ui;

import model.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

// AttackOnTitanApp
public class AttackOnTitanApp {
    private Player player;
    private ArrayList<Item> shopItems;
    private ArrayList<Titan> titans;
    private Scanner input;

    public static final Integer MAX_CHARS = 26;
    public static final Integer MIN_CHARS = 2;

    public AttackOnTitanApp() {
        init();
    }

    public void init() {
        input = new Scanner(System.in);
        input.useDelimiter("\n");
        //prompt user for their name
        System.out.println("Please enter your name: ");
        String s = input.next();
        while (!validName(s)) {
            System.out.println("Please enter a valid name (no spaces, length between " + MIN_CHARS.toString() + " and " + MAX_CHARS.toString() + ").");
            s = input.next();
        }
        player = new Player(s);

        //initialize the titans
        titans = new ArrayList<>(Arrays.asList(
                new Titan("Connie"),
                new Titan("Sasha"),
                new Titan("Eren"),
                new Titan("Mikasa"),
                new Titan("Armin"),
                new Titan("Reiner"),
                new Titan("Bertholdt"),
                new Titan("Annie"),
                new Titan("Jeke")));

        //initialize the shop
        shopItems = new ArrayList<>(Arrays.asList(
                new Item("Rusty Kitchen Knife", 1),
                new Item("Meat Pie for Sasha", 3),
                new Item("Zeke's Baseball", 4),
                new Item("Sword", 10),
                new Item("ODM Gear", 50),
                new Item("Thunder Spear",  100),
                new Item("Founding Titan Fluid", 999)));
    }

    boolean validName(String s) {
        if (s.length() >= MAX_CHARS || s.length() <= MIN_CHARS) {
            return false;
        }
        if (s.contains(" ")) {
            return false;
        }
        return true;
    }
}
