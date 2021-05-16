package persistence;

import model.Leaderboard;
import model.Player;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonWriterTest extends JsonTest {
    //NOTE TO CPSC 210 STUDENTS: the strategy in designing tests for the JsonWriter is to
    //write data to a file and then use the reader to read it back in and check that we
    //read in a copy of what was written out.

    @Test
    void testWriterInvalidFile() {
        try {
            Leaderboard lb = new Leaderboard();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyWorkroom() {
        try {
            Leaderboard lb = new Leaderboard();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyLeaderboard.json");
            writer.open();
            writer.write(lb);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyLeaderboard.json");
            lb = reader.read();
            assertEquals(0, lb.getLeaderboard().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWorkroom() {
        try {
            Leaderboard lb = new Leaderboard();
            Player p0 = new Player("Sam");
            p0.setWins(2);
            p0.setLosses(3);
            Player p1 = new Player("Bob");
            p1.setWins(4);
            p1.setLosses(1);

            lb.findOrAddPlayer(p0);
            lb.findOrAddPlayer(p1);

            JsonWriter writer = new JsonWriter("./data/testWriterGeneralLeaderboard.json");
            writer.open();
            writer.write(lb);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralLeaderboard.json");
            lb = reader.read();
            List<Player> players = lb.getLeaderboard();
            assertEquals(2, players.size());
            checkPlayer("Sam", 2, 3, players.get(0));
            checkPlayer("Bob", 4, 1, players.get(1));
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}