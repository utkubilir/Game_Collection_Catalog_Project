import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

public class JsonHandler {

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    // Read JSON file and convert it to a List of Maps
    public List<Map<String, Object>> readJsonFile(String filePath) {
        try (Reader reader = new FileReader(filePath)) {
            Type listType = new TypeToken<List<Map<String, Object>>>() {}.getType();
            return gson.fromJson(reader, listType);
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    // Write a List of Maps to a JSON file
    public void writeJsonFile(String filePath, List<Map<String, Object>> gamesList) {
        try (Writer writer = new FileWriter(filePath)) {
            gson.toJson(gamesList, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        JsonHandler handler = new JsonHandler();
        String filePath = "games.json";

        // Read JSON
        List<Map<String, Object>> games = handler.readJsonFile(filePath);
        System.out.println("Games List: " + games);

        // Modify Data (Example: Add a new game)
        Map<String, Object> newGame = new HashMap<>();
        newGame.put("title", "New Game");
        newGame.put("developer", "Indie Studio");
        newGame.put("release_year", 2025);
        games.add(newGame);

        // Write back to JSON
        handler.writeJsonFile(filePath, games);
        System.out.println("Updated JSON saved.");
    }
}
