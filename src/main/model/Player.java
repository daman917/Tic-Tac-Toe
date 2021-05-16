package model;

import org.json.JSONObject;
import persistence.Writable;

import java.util.Objects;

// records info about every player
public class Player implements Writable {
    private String name;
    private int wins = 0;
    private int losses = 0;
    private Leaderboard leaderboard;

    // EFFECTS: constructs player and sets name
    public Player(String name) {
        this.name = name;
    }

    // MODIFIES: this
    // EFFECTS: adds one win
    public void addWin() {
        ++wins;
    }

    // MODIFIES: this
    // EFFECTS: adds one loss
    public void addLoss() {
        ++losses;
    }

    // REQUIRES: leaderboard != null
    // MODIFIES: leaderboard
    //EFFECTS: returns what rank this player is on the leaderboard after sorting it
    public int getRank() {
        leaderboard.sortLeaderboard();
        int playerIndex = leaderboard.getLeaderboard().indexOf(this);
        return ++playerIndex;
    }

    public int getWins() {
        return wins;
    }

    public int getLosses() {
        return losses;
    }

    public String getName() {
        return name;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

    public Leaderboard getLeaderboard() {
        return leaderboard;
    }

    public void setLeaderboard(Leaderboard leaderboard) {
        this.leaderboard = leaderboard;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("Name", name);
        json.put("Wins", wins);
        json.put("Losses", losses);
        return json;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Player player = (Player) o;
        return name.equals(player.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
