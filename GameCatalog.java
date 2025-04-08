import org.json.JSONArray;
import org.json.JSONObject;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;

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

    
}