package persistence;

import model.Leaderboard;
import model.Player;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Represents a reader that reads leaderboard from JSON data stored in file (based on sample workroom application)
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads leaderboard from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Leaderboard read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseLeaderboard(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(contentBuilder::append);
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses leaderboard from JSON object and returns it
    private Leaderboard parseLeaderboard(JSONObject jsonObject) {
        Leaderboard lb = new Leaderboard();
        addPlayers(lb, jsonObject);
        return lb;
    }

    // MODIFIES: lb
    // EFFECTS: parses players from JSON object and adds them to leaderboard
    private void addPlayers(Leaderboard lb, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("Players");
        for (Object json : jsonArray) {
            JSONObject nextPlayer = (JSONObject) json;
            addPlayer(lb, nextPlayer);
        }
    }

    // MODIFIES: lb
    // EFFECTS: parses player from JSON object and adds it to leaderboard
    private void addPlayer(Leaderboard lb, JSONObject jsonObject) {
        String name = jsonObject.getString("Name");
        int wins = jsonObject.getInt("Wins");
        int losses = jsonObject.getInt("Losses");
        Player player = new Player(name);

        player.setWins(wins);

        player.setLosses(losses);

        lb.findOrAddPlayer(player);
    }
}
