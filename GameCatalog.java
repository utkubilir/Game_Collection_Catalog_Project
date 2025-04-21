import org.json.JSONArray;
import org.json.JSONObject;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Comparator;

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
    public List<Game> sortGames(String criteria, boolean ascending) {
    List<Game> gameList = games.toList().stream()
        .map(obj -> Game.fromJSONObject(new JSONObject((java.util.Map<?, ?>) obj)))
        .collect(Collectors.toList());

    Comparator<Game> comparator;

    switch (criteria.toLowerCase()) {
        case "title":
            comparator = Comparator.comparing(Game::getTitle, String.CASE_INSENSITIVE_ORDER);
            break;
        case "releaseyear":
            comparator = Comparator.comparingInt(Game::getReleaseYear);
            break;
        case "playtime":
            comparator = Comparator.comparingInt(Game::getPlaytime);
            break;
        default:
            comparator = Comparator.comparing(Game::getTitle, String.CASE_INSENSITIVE_ORDER);
            break;
    }

    if (!ascending) {
        comparator = comparator.reversed();
    }

    gameList.sort(comparator);
    return gameList;
}
    
}