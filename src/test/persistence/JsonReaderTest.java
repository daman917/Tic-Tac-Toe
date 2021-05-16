package persistence;

import model.Leaderboard;
import model.Player;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Leaderboard lb = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyWorkRoom() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyLeaderboard.json");
        try {
            Leaderboard lb = reader.read();
            assertEquals(0, lb.getLeaderboard().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralWorkRoom() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralLeaderboard.json");
        try {
            Leaderboard lb = reader.read();
            List<Player> players = lb.getLeaderboard();
            assertEquals(2, players.size());
            checkPlayer("Sam", 2, 3, players.get(0));
            checkPlayer("Bob", 4, 1, players.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}