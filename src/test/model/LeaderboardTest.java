package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class LeaderboardTest {
    Leaderboard l;
    Player p0 = new Player("David");
    Player p1 = new Player("Emma");

    @BeforeEach
    public void runBefore() {
        l = new Leaderboard();
    }

    @Test
    public void testFindOrAddPlayer() {
        l.findOrAddPlayer(p0);

        assertEquals(p1, l.findOrAddPlayer(p1));
        assertEquals(p0, l.findOrAddPlayer(p0));
        assertEquals(2, l.getLeaderboard().size());

        assertEquals(l, p0.getLeaderboard());
        assertEquals(l, p1.getLeaderboard());
    }

    @Test
    public void testSortLeaderboard() {
        p1.addWin();

        l.findOrAddPlayer(p0);
        l.findOrAddPlayer(p1);
        assertEquals(Arrays.asList(p0, p1), l.getLeaderboard());

        l.sortLeaderboard();
        assertEquals(Arrays.asList(p1, p0), l.getLeaderboard());
    }
}
