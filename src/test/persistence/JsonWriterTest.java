package persistence;

import model.*;
import model.player.Player;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

// DISCLAIMER: MOST OF THE CODE HERE WAS ADAPTED FROM THE PROVIDED REPOSITORY/PROJECT,
//             JsonSerializationDemo
class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            GameState gs = new GameState(new Player("", 0, new ArrayList<>(), null), new ArrayList<>(), new ArrayList<>(), null, null);
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyGameState() {

    }

    @Test
    void testWriterGeneralGameState() {

    }
}