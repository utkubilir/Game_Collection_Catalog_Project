import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.Comparator;
import java.util.List;

import com.google.gson.JsonObject;
import org.json.JSONArray;
import org.json.JSONObject;

    // we wrote this class to take the games and manage them
    // it filters, sort , etc.
public class GameCatalog {
    private JSONArray games;
    private final String filePath;

    public GameCatalog(String filePath) {
        this.filePath=filePath;
        this.games = new JSONArray();
        loadGames();
    }

    //reads json file and puts them into a jsonarray
    public void loadGames() {
        try {
            String content = new String(Files.readAllBytes(Paths.get(filePath)));
            games = new JSONArray(content);
        } catch (IOException e) {
            System.out.println("Could not load file " + filePath);
            e.printStackTrace();
        }
    }

    //writes the readed games to the json file
    public void saveGames() {
        try {
            Files.write(Paths.get(filePath), games.toString(4).getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // adds a new game as an json object to the array
    public void addGame(JSONObject game) {
        games.put(game);
        saveGames();
    }

    // filters games with tags
    public List<Game> filterGamesByTags(List<String> selectedTags) {
        return games.toList().stream()
                .map(obj -> Game.fromJSONObject(new JSONObject((Map<?, ?>) obj)))
                .filter(game -> game.getTags().stream().anyMatch(selectedTags::contains))
                .collect(Collectors.toList());
    }

    //sorts the games with wanted criteria
    public List<Game> sortGames(String criteria, boolean ascending) {
        List<Game> gameList = games.toList().stream()
                .map(obj -> Game.fromJSONObject(new JSONObject((Map<?, ?>) obj)))
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

    //it filters games by developer
    public List<Game> filterGamesByDeveloper(String developer) {
        return games.toList().stream()
                .map(obj -> Game.fromJSONObject(new JSONObject((Map<?, ?>) obj)))
                .filter(game -> game.getDeveloper().equalsIgnoreCase(developer))
                .collect(Collectors.toList());
    }

    public List<Game> searchGame(String criterion){
        return games.toList().stream();
        .map(obj -> Game.fromJSONObject(new JsonObject((Map<?.?>) obj)));
        .filter(game -> game.getTitle().toLowerCase.contains(criterion.toLowerCase()));
        .collect(Collectors.toList());

    }

    //writes selected games
    public void exportSelectedGames(List<Game> selectedGames) {
        JSONArray selectedJsonGames = new JSONArray();
        for (Game game : selectedGames) {
            selectedJsonGames.put(game.toJSONObject());
        }
        try {
            Files.write(Paths.get("selected_games.json"), selectedJsonGames.toString(4).getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
