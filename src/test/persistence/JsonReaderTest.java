package persistence;

import model.*;
import model.player.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

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

//    @Test
//    void testReaderEmptyWorkRoom() {
//        JsonReader reader = new JsonReader("./data/testReaderEmptyGameState.json");
//        try {
//            GameState gs = reader.read();
//            assertEquals(gs.getTitans().size(), 0);
//            Player p = new Player("", 0, new ArrayList<>(), null);
//            checkPlayer(p, gs.getPlayer());
//        } catch (IOException e) {
//            fail("Couldn't read from file");
//        }
//    }
//
//    @Test
//    void testReaderGeneralWorkRoom() {
//    }
}