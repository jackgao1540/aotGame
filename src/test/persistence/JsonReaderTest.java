package persistence;

import model.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

// DISCLAIMER: MOST OF THE CODE HERE WAS ADAPTED FROM THE PROVIDED REPOSITORY/PROJECT,
//             JsonSerializationDemo
class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            GameState wr = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyWorkRoom() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyGameState.json");
        try {
            GameState gs = reader.read();
            assertEquals(gs.getTitans().size(), 0);
            Player p = new Player("", 0, new ArrayList<>());
            checkPlayer(p, gs.getPlayer());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralWorkRoom() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralGameState.json");
        try {
            GameState gs = reader.read();
            ArrayList<Item> items = new ArrayList<>(Arrays.asList(
                    new Item("Enma", -1),
                    new Item("Immortal Shieldbow", 3400),
                    new Item("Reshiram", 0)
            ));
            Player p = new Player("jjdaboss1540", 42069, items);
            checkPlayer(p, gs.getPlayer());
            ArrayList<Titan> titans = new ArrayList<>(Arrays.asList(
                new Titan("Zunesha", true, 12345),
                new Titan("Optimus", true, 0)
            ));
            checkTitans(gs.getTitans(), titans);
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}