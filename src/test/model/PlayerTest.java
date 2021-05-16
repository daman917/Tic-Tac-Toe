package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {
    Player p0;
    Player p1;
    Leaderboard l;

    @BeforeEach
    public void runBefore() {
        p0 = new Player("Sam");
        p1 = new Player("Bob");
        l = new Leaderboard();
    }

    @Test
    public void testInit() {
        assertEquals("Sam", p0.getName());
    }

    @Test
    public void testAddWin() {
        p0.addWin();
        assertEquals(1, p0.getWins());
    }

    @Test
    public void testAddLoss() {
        p0.addLoss();
        assertEquals(1, p0.getLosses());
    }

    @Test
    public void testGetRank() {
        l.findOrAddPlayer(p0);
        l.findOrAddPlayer(p1);

        p1.addWin();

        assertEquals(1, p1.getRank());
        assertEquals(2, p0.getRank());
    }
}
