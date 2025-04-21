import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class JsonHandler {

    private static final String DEFAULT_FILE_PATH = "games.json";

    // reads JSON file and return list of game maps
    public List<Map<String, Object>> readJsonFile(String filePath) {
        List<Map<String, Object>> gameList = new ArrayList<>();

        try {
            String content = new String(Files.readAllBytes(Paths.get(filePath)));
            JSONArray jsonArray = new JSONArray(content);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject gameJson = jsonArray.getJSONObject(i);
                Map<String, Object> gameMap = new HashMap<>();

                for (String key : gameJson.keySet()) {
                    gameMap.put(key, gameJson.get(key));
                }

                gameList.add(gameMap);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return gameList;
    }

    // writes list of game maps to JSON file
    public void writeJsonFile(String filePath, List<Map<String, Object>> gamesList) {
        JSONArray jsonArray = new JSONArray();

        for (Map<String, Object> game : gamesList) {
            JSONObject gameJson = new JSONObject(game);
            jsonArray.put(gameJson);
        }

        try {
            Files.write(Paths.get(filePath), jsonArray.toString(4).getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // helper method to create a game entry as a map
    public Map<String, Object> createGame(String title, String developer, int releaseYear) {
        Map<String, Object> game = new HashMap<>();
        game.put("title", title);
        game.put("developer", developer);
        game.put("releaseYear", releaseYear);
        return game;
    }

    // just a test
    public static void main(String[] args) {
        GameCatalog catalog = new GameCatalog();

        // Show all loaded games
        System.out.println("=== All Games from JSON ===");
        List<Game> allGames = catalog.sortGames("title", true);
        for (Game game : allGames) {
            System.out.println("- " + game.getTitle()
                    + " | Developer: " + game.getDeveloper()
                    + " | Release: " + game.getReleaseYear());
        }

        // Create a new game to test addition
        Game testGame = new Game(
                "Deep Horizon",
                List.of("Sci-Fi", "RPG"),
                "Nova Studios",
                "DeepSpace Inc.",
                List.of("PC", "PS5"),
                List.of("Translator X", "Translator Y"),
                "987654",
                2026,
                60,
                "Digital",
                "English",
                "Teen",
                List.of("Space", "Exploration", "Story Rich"),
                "deephorizon.jpg"
        );

        // Add and save the new game
        catalog.addGame(testGame.toJSONObject());
        System.out.println("\nNew game 'Deep Horizon' added.");

        // Filter games with the tag "Story Rich"
        List<Game> filteredByTag = catalog.filterGamesByTags(List.of("Story Rich"));
        System.out.println("\n=== Games tagged with 'Story Rich' ===");
        for (Game g : filteredByTag) {
            System.out.println("- " + g.getTitle());
        }

        // Filter games by a developer
        List<Game> byDeveloper = catalog.filterGamesByDeveloper("Nova Studios");
        System.out.println("\n=== Games by 'Nova Studios' ===");
        for (Game g : byDeveloper) {
            System.out.println("- " + g.getTitle());
        }

        // Export filtered list
        catalog.exportSelectedGames(byDeveloper);
        System.out.println("\nExported 'Nova Studios' games to selected_games.json.");
    }
}

