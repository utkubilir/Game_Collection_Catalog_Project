import java.io.*;
import java.util.*;

public class JsonHandler {

    // JSON formatında veriyi okuma
    public List<Map<String, String>> readJsonFile(String filePath) {
        List<Map<String, String>> gamesList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            StringBuilder jsonContent = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                jsonContent.append(line);
            }

            String jsonData = jsonContent.toString().replace("[", "").replace("]", "").trim();
            String[] games = jsonData.split("},\\{");

            for (String game : games) {
                Map<String, String> gameMap = new HashMap<>();
                game = game.replace("{", "").replace("}", "").trim();
                String[] keyValues = game.split(",");
                for (String keyValue : keyValues) {
                    String[] parts = keyValue.split(":");
                    gameMap.put(parts[0].trim().replace("\"", ""), parts[1].trim().replace("\"", ""));
                }
                gamesList.add(gameMap);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return gamesList;
    }
}