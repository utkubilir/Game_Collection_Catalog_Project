package controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Game;
import util.JsonHandler;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class AddGameController {

    @FXML private TextField titleField;
    @FXML private TextField genreArea;
    @FXML private TextField developerField;
    @FXML private TextField publisherField;
    @FXML private TextField platformsArea;
    @FXML private TextField translatorsArea;
    @FXML private TextField steamIdField;
    @FXML private TextField releaseYearField;
    @FXML private TextField playtimeField;
    @FXML private TextField formatField;
    @FXML private TextField languageField;
    @FXML private TextField ratingField;
    @FXML private TextField tagsArea;
    @FXML private TextField imagePathField;
    @FXML private ImageView coverPreview;

    private final String jsonPath = "games.json";

    @FXML
    private void handleSaveGame() {
        try {
            Game game = new Game();
            game.setTitle(titleField.getText());
            game.setGenre(splitText(genreArea.getText()));
            game.setDeveloper(developerField.getText());
            game.setPublisher(publisherField.getText());
            game.setPlatforms(splitText(platformsArea.getText()));
            game.setTranslators(splitText(translatorsArea.getText()));
            game.setSteamId(steamIdField.getText());
            game.setReleaseYear(Integer.parseInt(releaseYearField.getText()));
            game.setPlaytime(Double.parseDouble(playtimeField.getText()));
            game.setFormat(formatField.getText());
            game.setLanguage(languageField.getText());
            game.setRating(Double.parseDouble(ratingField.getText()));
            game.setTags(splitText(tagsArea.getText()));
            game.setImagePath(imagePathField.getText());

            List<Game> games = new ArrayList<>();
            if (Files.exists(Paths.get(jsonPath))) {
                games = JsonHandler.readGames(jsonPath);
            }

            games.add(game);
            JsonHandler.writeGames(games, jsonPath);

            showAlert("Success", "Game saved successfully.");
            clearForm();

        } catch (Exception e) {
            showAlert("Error", "Invalid input: " + e.getMessage());
        }
    }

    @FXML
    private void handleClear() {
        clearForm();
    }

    private void clearForm() {
        titleField.clear();
        genreArea.clear();
        developerField.clear();
        publisherField.clear();
        platformsArea.clear();
        translatorsArea.clear();
        steamIdField.clear();
        releaseYearField.clear();
        playtimeField.clear();
        formatField.clear();
        languageField.clear();
        ratingField.clear();
        tagsArea.clear();
        imagePathField.clear();
        coverPreview.setImage(null);
    }

    private List<String> splitText(String input) {
        if (input == null || input.isBlank()) return new ArrayList<>();
        return Arrays.asList(input.split("\\s*,\\s*"));
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void handleBrowseImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Cover Image");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );
        File selectedFile = fileChooser.showOpenDialog(titleField.getScene().getWindow());

        if (selectedFile != null) {
            try {
                File destDir = new File("images");
                if (!destDir.exists()) destDir.mkdir();

                String uniqueName = UUID.randomUUID().toString() + "_" + selectedFile.getName();
                File destFile = new File(destDir, uniqueName);
                Files.copy(selectedFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

                imagePathField.setText(destFile.getPath());
                coverPreview.setImage(new Image(destFile.toURI().toString()));
            } catch (IOException e) {
                showAlert("Error", "Image copy failed: " + e.getMessage());
            }
        }
    }
}
