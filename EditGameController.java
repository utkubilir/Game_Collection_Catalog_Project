package controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Game;
import util.JsonHandler;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.io.IOException;

public class EditGameController {
    private static Game editingGame;

    public static void setEditingGame(Game game) {
        editingGame = game;
    }

    @FXML private TextField titleField;
    @FXML private TextField developerField;
    @FXML private TextField releaseYearField;
    @FXML private TextField ratingField;
    @FXML private TextArea genreArea;
    @FXML private TextArea platformsArea;
    @FXML private TextArea tagsArea;
    @FXML private TextField formatField;
    @FXML private TextField languageField;
    @FXML private TextField playtimeField;
    @FXML private TextField imagePathField;

    @FXML
    private void initialize() {
        if (editingGame != null) {
            titleField.setText(editingGame.getTitle());
            developerField.setText(editingGame.getDeveloper());
            releaseYearField.setText(String.valueOf(editingGame.getReleaseYear()));
            ratingField.setText(String.valueOf(editingGame.getRating()));
            genreArea.setText(String.join(", ", editingGame.getGenre()));
            platformsArea.setText(String.join(", ", editingGame.getPlatforms()));
            tagsArea.setText(String.join(", ", editingGame.getTags()));
            formatField.setText(editingGame.getFormat());
            languageField.setText(editingGame.getLanguage());
            playtimeField.setText(String.valueOf(editingGame.getPlaytime()));
            imagePathField.setText(editingGame.getImagePath());
        }
    }

    @FXML
    private void handleSaveChanges() {
        try {
            editingGame.setTitle(titleField.getText());
            editingGame.setDeveloper(developerField.getText());
            editingGame.setReleaseYear(Integer.parseInt(releaseYearField.getText()));
            editingGame.setRating(Double.parseDouble(ratingField.getText()));
            editingGame.setGenre(splitText(genreArea.getText()));
            editingGame.setPlatforms(splitText(platformsArea.getText()));
            editingGame.setTags(splitText(tagsArea.getText()));
            editingGame.setFormat(formatField.getText());
            editingGame.setLanguage(languageField.getText());
            editingGame.setPlaytime(Double.parseDouble(playtimeField.getText()));
            editingGame.setImagePath(imagePathField.getText());

            List<Game> allGames = JsonHandler.readGames("games.json");
            for (int i = 0; i < allGames.size(); i++) {
                if (allGames.get(i).getSteamId().equals(editingGame.getSteamId())) {
                    allGames.set(i, editingGame);
                    break;
                }
            }
            JsonHandler.writeGames(allGames, "games.json");
            ((Stage) titleField.getScene().getWindow()).close();

        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }

    private List<String> splitText(String text) {
        if (text == null || text.isBlank()) return List.of();
        return Arrays.stream(text.split(","))
                     .map(String::trim)
                     .collect(Collectors.toList());
    }
}