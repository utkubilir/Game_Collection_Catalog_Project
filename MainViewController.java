package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Game;
import util.JsonHandler;

import java.io.File;
import java.io.FileWriter;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.util.List;

public class MainViewController {

    @FXML
    private void handleNewGame() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AddGameView.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Add New Game");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleViewGames() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/GameListView.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Game List");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleImportJson() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Import JSON File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON Files", "*.json"));
        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            try (Reader reader = Files.newBufferedReader(file.toPath())) {
                Gson gson = new Gson();
                Type listType = new TypeToken<List<Game>>() {}.getType();
                List<Game> importedGames = gson.fromJson(reader, listType);

                List<Game> existingGames = JsonHandler.readGames("games.json");
                for (Game game : importedGames) {
                    boolean exists = existingGames.stream()
                            .anyMatch(g -> g.getSteamId().equals(game.getSteamId()));
                    if (!exists) {
                        existingGames.add(game);
                    }
                }

                JsonHandler.writeGames(existingGames, "games.json");
                showAlert("Success", "Games imported successfully.");

            } catch (Exception e) {
                e.printStackTrace();
                showAlert("Error", "Failed to import JSON: " + e.getMessage());
            }
        }
    }

    @FXML
    private void handleExportJson() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Export JSON File");
        fileChooser.setInitialFileName("exported_games.json");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON Files", "*.json"));
        File file = fileChooser.showSaveDialog(null);

        if (file != null) {
            try (FileWriter writer = new FileWriter(file)) {
                List<Game> games = JsonHandler.readGames("games.json");
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                gson.toJson(games, writer);
                showAlert("Success", "Games exported successfully.");
            } catch (Exception e) {
                e.printStackTrace();
                showAlert("Error", "Failed to export JSON: " + e.getMessage());
            }
        }
    }

    @FXML
    private void handleExit() {
        System.exit(0);
    }

    @FXML
    private void handleHelp() {
        // Help menu action placeholder
    }

    private void showAlert(String title, String msg) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
