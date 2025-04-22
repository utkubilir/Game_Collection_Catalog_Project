package util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.Game;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class JsonHandler {
    private static final Gson gson = new Gson();

    public static List<Game> readGames(String filePath) throws IOException {
        FileReader reader = new FileReader(filePath);
        Type listType = new TypeToken<List<Game>>(){}.getType();
        List<Game> games = gson.fromJson(reader, listType);
        reader.close();
        return games;
    }

    public static void writeGames(List<Game> games, String filePath) throws IOException {
        FileWriter writer = new FileWriter(filePath);
        gson.toJson(games, writer);
        writer.flush();
        writer.close();
    }
}
