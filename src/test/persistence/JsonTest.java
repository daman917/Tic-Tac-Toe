package persistence;

import model.Player;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkPlayer(String name, int wins, int losses, Player p) {
        assertEquals(name, p.getName());
        assertEquals(wins, p.getWins());
        assertEquals(losses, p.getLosses());
    }
}
