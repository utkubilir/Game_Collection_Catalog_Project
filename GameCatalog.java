import org.json.JSONArray;
import org.json.JSONObject;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class GameCatalog {

    private JSONArray games;

    public GameCatalog() {
        games = new JSONArray();
        loadGames();
    }

    public void loadGames() {
        try {
            String content = new String(Files.readAllBytes(Paths.get("games.json")));
            games = new JSONArray(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveGames() {
        try {
            Files.write(Paths.get("games.json"), games.toString(4).getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addGame(JSONObject game) {
        games.put(game);
        saveGames();
    }

    public List<Game> filterGamesByTags(List<String> selectedTags) {
        return games.toList().stream()
            .map(obj -> Game.fromJSONObject(new JSONObject((java.util.Map<?, ?>) obj)))
            .filter(game -> game.getTags().stream().anyMatch(selectedTags::contains))
            .collect(Collectors.toList());
    }
}