package controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Game;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class EditGameController {

    @FXML private TextField titleField;
    @FXML private TextField developerField;
    @FXML private TextField publisherField;
    @FXML private TextField releaseYearField;
    @FXML private TextField ratingField;
    @FXML private TextArea genreArea;
    @FXML private TextArea platformsArea;
    @FXML private TextArea tagsArea;
    @FXML private TextField formatField;
    @FXML private TextField languageField;
    @FXML private TextField playtimeField;
    @FXML private TextField steamIdField;
    @FXML private TextField imagePathField;
    @FXML private ImageView coverPreview;

    private Game editingGame;

    public void setEditingGame(Game game) {
        this.editingGame = game;
        populateFields();
    }

    private void populateFields() {
        titleField.setText(editingGame.getTitle());
        developerField.setText(editingGame.getDeveloper());
        publisherField.setText(editingGame.getPublisher());
        releaseYearField.setText(String.valueOf(editingGame.getReleaseYear()));
        ratingField.setText(String.valueOf(editingGame.getRating()));
        genreArea.setText(String.join(", ", editingGame.getGenre()));
        platformsArea.setText(String.join(", ", editingGame.getPlatforms()));
        tagsArea.setText(String.join(", ", editingGame.getTags()));
        formatField.setText(editingGame.getFormat());
        languageField.setText(editingGame.getLanguage());
        playtimeField.setText(String.valueOf(editingGame.getPlaytime()));
        steamIdField.setText(editingGame.getSteamId());
        imagePathField.setText(editingGame.getImagePath());

        if (editingGame.getImagePath() != null && new File(editingGame.getImagePath()).exists()) {
            coverPreview.setImage(new Image(new File(editingGame.getImagePath()).toURI().toString()));
        }
    }

    @FXML
    private void handleBrowseImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Cover Image");
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            imagePathField.setText(file.getAbsolutePath());
            coverPreview.setImage(new Image(file.toURI().toString()));
        }
    }

    @FXML
    private void handleSaveGame() {
        try {
            editingGame.setTitle(titleField.getText());
            editingGame.setDeveloper(developerField.getText());
            editingGame.setPublisher(publisherField.getText());
            editingGame.setReleaseYear(Integer.parseInt(releaseYearField.getText()));
            editingGame.setRating(Double.parseDouble(ratingField.getText()));
            editingGame.setGenre(splitText(genreArea.getText()));
            editingGame.setPlatforms(splitText(platformsArea.getText()));
            editingGame.setTags(splitText(tagsArea.getText()));
            editingGame.setFormat(formatField.getText());
            editingGame.setLanguage(languageField.getText());
            editingGame.setPlaytime(Double.parseDouble(playtimeField.getText()));
            editingGame.setSteamId(steamIdField.getText());
            editingGame.setImagePath(imagePathField.getText());
    
            List<Game> games = util.JsonHandler.readGames("games.json");
    
            for (int i = 0; i < games.size(); i++) {
                if (games.get(i).getSteamId().equals(editingGame.getSteamId())) {
                    games.set(i, editingGame);
                    break;
                }
            }
    
            util.JsonHandler.writeGames(games, "games.json");
    
            ((Stage) titleField.getScene().getWindow()).close();
    
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void handleClear() {
        populateFields();
    }

    private List<String> splitText(String input) {
        return Arrays.asList(input.split("\\s*,\\s*"));
    }
}
