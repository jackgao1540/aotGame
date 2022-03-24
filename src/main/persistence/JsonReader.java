package persistence;

import model.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

import model.buildings.Shop;
import model.buildings.TownHall;
import model.buildings.TownHouse;
import model.Item;
import model.player.Player;
import model.titans.Titan;
import org.json.*;

// DISCLAIMER: MOST OF THE CODE HERE WAS ADAPTED FROM THE PROVIDED REPOSITORY/PROJECT,
//             JsonSerializationDemo
// An object that can read a GameState from a JSON file
public class JsonReader {
    private String source; //source file to be loaded

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads a GameState file and returns the GameState
    // throws IOException if an error occurs reading data from file
    public GameState read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseGameState(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses GameState from JSON object and returns it
    private GameState parseGameState(JSONObject jsonObject) {
        Player player = parsePlayer(jsonObject);
        ArrayList<Titan> titans = parseTitans(jsonObject);
        ArrayList<TownHouse> houses = parseHouses(jsonObject);
        TownHall th = parseTownHall(jsonObject);
        Shop s = parseShop(jsonObject);
        GameState gs = new GameState(player, titans, houses, th, s);
        //set the gamestates
        player.setGameState(gs);
        for (Titan t: titans) {
            t.setGameState(gs);
        }
        for (TownHouse townHouse : houses) {
            townHouse.setGameState(gs);
        }
        th.setGameState(gs);
        s.setGameState(gs);
        return gs;
    }

    public TownHall parseTownHall(JSONObject jsonObject) {
        JSONObject json = jsonObject.getJSONObject("TownHall");
        int hp = json.getInt("hitPoints");
        return new TownHall(hp, null);
    }

    public Shop parseShop(JSONObject jsonObject) {
        JSONObject json = jsonObject.getJSONObject("Shop");
        int hp = json.getInt("hitPoints");
        return new Shop(hp, null);
    }

    public ArrayList<TownHouse> parseHouses(JSONObject json) {
        ArrayList<TownHouse> townHouses = new ArrayList<>();
        JSONArray arr = json.getJSONArray("buildings");
        for (Object j : arr) {
            JSONObject townHouse = (JSONObject)j;
            int x = townHouse.getInt("x");
            int y = townHouse.getInt("y");
            int hp = townHouse.getInt("hitPoints");
            int r = townHouse.getInt("rotation");
            TownHouse th = new TownHouse(x, y, hp, r, null);
            townHouses.add(th);
        }
        return townHouses;
    }

    // EFFECTS: parses list of titans from JSON object and returns it
    private ArrayList<Titan> parseTitans(JSONObject jsonObject) {
        ArrayList<Titan> titans = new ArrayList<>();
        JSONArray i = jsonObject.getJSONArray("titans");
        for (Object json : i) {
            JSONObject nextTitan = (JSONObject)json;
            int r = nextTitan.getInt("reward");
            int x = nextTitan.getInt("x");
            int y = nextTitan.getInt("y");
            int w = nextTitan.getInt("width");
            int h = nextTitan.getInt("height");
            Titan tighten = new Titan(r, x, y, w, h, null);
            titans.add(tighten);
        }
        return titans;
    }

    // EFFECTS: parses player from JSON objet and returns it
    private Player parsePlayer(JSONObject json) {
        JSONObject p = json.getJSONObject("player");
        String name = p.getString("name");
        int money = p.getInt("money");
        ArrayList<Item> items = parseItems(p);
        int x = p.getInt("x");
        int y = p.getInt("y");
        Player player = new Player(name, money, items, x, y, null);
        return player;
    }

    // EFFECTS: parses list of items from JSON object and returns it
    private ArrayList<Item> parseItems(JSONObject jsonObject) {
        ArrayList<Item> items = new ArrayList<>();
        JSONArray i = jsonObject.getJSONArray("items");
        for (Object json : i) {
            JSONObject nextItem = (JSONObject)json;
            String name = nextItem.getString("name");
            int price = nextItem.getInt("price");
            int atk = nextItem.getInt("atk");
            int spd = nextItem.getInt("spd");
            boolean cf = nextItem.getBoolean("canFire");
            int fd = nextItem.getInt("fireDMG");
            int ps = nextItem.getInt("projSize");
            boolean cd = nextItem.getBoolean("canDash");
            int dd = nextItem.getInt("dashDMG");
            Item item = new Item(name, price, atk, spd, cf, fd, ps, cd, dd);
            items.add(item);
        }
        return items;
    }
}
