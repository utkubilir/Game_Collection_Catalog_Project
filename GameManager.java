//package model;
//utku bende dosya bu konumda olmadığı için error verdi ondan package yazmadım

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import model.Game;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class GameManager {
     private static final Gson gson = new Gson();
    private static final Type gameListType = new TypeToken<List<Game>>() {}.getType();

    public static void saveGames(List<Game> games, String filename) throws IOException {
        try (Writer writer = new FileWriter(filename)) {
            gson.toJson(games, writer);
        }
    }

    public static List<Game> loadGames(String filename) throws IOException {
        File file = new File(filename);
        if (!file.exists()) return new ArrayList<>();

        try (Reader reader = new FileReader(file)) {
            return gson.fromJson(reader, gameListType);
        }
    }
}
