package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

// Leaderboard containing all players, ranked by how many more wins than losses they have
public class Leaderboard implements Writable {
    private List<Player> leaderboard;

    // EFFECTS: constructs leaderboard to be used for ranking players
    public Leaderboard() {
        leaderboard = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: if player is already in leaderboard, returns that one, else adds player and returns it
    public Player findOrAddPlayer(Player p0) {
        for (Player p1: leaderboard) {
            if (p0.equals(p1)) {
                return p1;
            }
        }

        leaderboard.add(p0);
        p0.setLeaderboard(this);
        return p0;
    }

    // MODIFIES: this
    // EFFECTS: sorts leaderboard, best to worst, based on how many more wins than losses players have
    public void sortLeaderboard() {
        for (int i = 1; i < leaderboard.size(); i++) {
            for (int a = 1; a < leaderboard.size(); a++) {
                int diff1 = leaderboard.get(a - 1).getWins() - leaderboard.get(a - 1).getLosses();
                int diff2 = leaderboard.get(a).getWins() - leaderboard.get(a).getLosses();

                if (diff1 < diff2) {
                    Player p = leaderboard.remove(a);
                    leaderboard.add(a - 1, p);
                }
            }
        }
    }

    public List<Player> getLeaderboard() {
        return leaderboard;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("Players", playersToJson());
        return json;
    }

    // EFFECTS: returns players in this leaderboard as a JSON array
    private JSONArray playersToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Player p: leaderboard) {
            jsonArray.put(p.toJson());
        }

        return jsonArray;
    }
}
