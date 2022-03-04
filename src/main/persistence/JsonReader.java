package persistence;

import model.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.json.*;

// Represents a reader that reads workroom from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads workroom from file and returns it;
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

    // EFFECTS: parses workroom from JSON object and returns it
    private GameState parseGameState(JSONObject jsonObject) {
        Player player = parsePlayer(jsonObject);
        ArrayList<Titan> titans = parseTitans(jsonObject);
        GameState gs = new GameState(player, titans);
        return gs;
    }

    private ArrayList<Titan> parseTitans(JSONObject jsonObject) {
        ArrayList<Titan> titans = new ArrayList<>();
        JSONArray i = jsonObject.getJSONArray("titans");
        for (Object json : i) {
            JSONObject nextTitan = (JSONObject)json;
            String name = nextTitan.getString("name");
            boolean state = nextTitan.getBoolean("state");
            int reward = nextTitan.getInt("reward");
            Titan tighten = new Titan(name, state, reward);
            titans.add(tighten);
        }
        return titans;
    }

    private Player parsePlayer(JSONObject json) {
        JSONObject p = json.getJSONObject("player");
        String name = p.getString("name");
        int money = p.getInt("money");
        ArrayList<Item> items = parseItems(p);
        Player player = new Player(name, money, items);
        return player;
    }

    private ArrayList<Item> parseItems(JSONObject jsonObject) {
        ArrayList<Item> items = new ArrayList<>();
        JSONArray i = jsonObject.getJSONArray("items");
        for (Object json : i) {
            JSONObject nextItem = (JSONObject)json;
            String name = nextItem.getString("name");
            int price = nextItem.getInt("price");
            Item item = new Item(name, price);
            items.add(item);
        }
        return items;
    }
}
