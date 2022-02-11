package model;

//Represents an item for the player to hold, having a name and a price.
public class Item {
    private String name;
    private int price;

    public Item(String n, int p) {
        this.name = n;
        this.price = p;
    }

    public int getPrice() {
        return this.price;
    }

    public String getName() {
        return this.name;
    }
}
