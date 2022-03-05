package persistence;

import model.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

// DISCLAIMER: MOST OF THE CODE HERE WAS ADAPTED FROM THE PROVIDED REPOSITORY/PROJECT,
//             JsonSerializationDemo
class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            GameState gs = new GameState(new Player("", 0, new ArrayList<>()), new ArrayList<>());
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyGameState() {
        try {
            GameState gs = new GameState(new Player("", 0, new ArrayList<>()), new ArrayList<>());
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyGameState.json");
            writer.open();
            writer.write(gs);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyGameState.json");
            gs = reader.read();
            Player p = new Player("", 0, new ArrayList<>());
            ArrayList<Titan> titans = new ArrayList<>();
            checkTitans(titans, gs.getTitans());
            checkPlayer(p, gs.getPlayer());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralGameState() {
        try {
            Player p = new Player("Jack", 100, new ArrayList<Item>(Arrays.asList(new Item("bum", 1))));
            ArrayList<Titan> titans = new ArrayList<>(Arrays.asList(new Titan("Eren", true, 100)));
            GameState gs = new GameState(p, titans);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralGameState.json");
            writer.open();
            writer.write(gs);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralGameState.json");
            gs = reader.read();
            checkPlayer(gs.getPlayer(), p);
            checkTitans(titans, gs.getTitans());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}