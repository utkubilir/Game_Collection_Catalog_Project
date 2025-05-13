package controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.*;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.Game;
import util.JsonHandler;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class GameListController {

    @FXML private TableView<Game> gameTable;
    @FXML private TableColumn<Game, String> titleCol, developerCol, publisherCol, releaseYearCol, ratingCol, genreCol, platformsCol, tagsCol;
    @FXML private TextField searchField;
    @FXML private ImageView coverPreview;
    @FXML private ListView<String> tagListView;

    private ObservableList<Game> gameList;

    @FXML
    private void initialize() {
        try {
            List<Game> games = JsonHandler.readGames("games.json");
            gameList = FXCollections.observableArrayList(games);

            titleCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTitle()));
            developerCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDeveloper()));
            publisherCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPublisher()));
            releaseYearCol.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getReleaseYear())));
            ratingCol.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getRating())));
            genreCol.setCellValueFactory(data -> new SimpleStringProperty(String.join(", ", data.getValue().getGenre())));
            platformsCol.setCellValueFactory(data -> new SimpleStringProperty(String.join(", ", data.getValue().getPlatforms())));
            tagsCol.setCellValueFactory(data -> new SimpleStringProperty(String.join(", ", data.getValue().getTags())));

            gameTable.setItems(gameList);

            Set<String> allTags = games.stream()
                    .flatMap(g -> g.getTags().stream())
                    .collect(Collectors.toCollection(TreeSet::new));

            tagListView.setItems(FXCollections.observableArrayList(allTags));
            tagListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            tagListView.getSelectionModel().getSelectedItems().addListener((ListChangeListener<String>) change -> applyFilters());

            gameTable.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> showCoverImage(newVal));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void applyFilters() {
        String keyword = searchField.getText().toLowerCase();
        List<String> selectedTags = new ArrayList<>(tagListView.getSelectionModel().getSelectedItems());
    
        FilteredList<Game> filtered = new FilteredList<>(gameList, game ->
            (keyword.isBlank() || matchesKeyword(game, keyword)) &&
            (selectedTags.isEmpty() || game.getTags().stream().anyMatch(selectedTags::contains))
        );
    
        gameTable.setItems(filtered);
    }
    
    private boolean matchesKeyword(Game game, String keyword) {
        return game.getTitle().toLowerCase().contains(keyword)
            || game.getDeveloper().toLowerCase().contains(keyword)
            || game.getPublisher().toLowerCase().contains(keyword)
            || game.getGenre().stream().anyMatch(s -> s.toLowerCase().contains(keyword))
            || game.getPlatforms().stream().anyMatch(s -> s.toLowerCase().contains(keyword))
            || game.getTranslators().stream().anyMatch(s -> s.toLowerCase().contains(keyword))
            || game.getSteamId().toLowerCase().contains(keyword)
            || String.valueOf(game.getReleaseYear()).contains(keyword)
            || String.valueOf(game.getPlaytime()).contains(keyword)
            || game.getFormat().toLowerCase().contains(keyword)
            || game.getLanguage().toLowerCase().contains(keyword)
            || String.valueOf(game.getRating()).contains(keyword)
            || game.getTags().stream().anyMatch(s -> s.toLowerCase().contains(keyword));
    }
    
    @FXML
    private void handleSearch() {
        applyFilters();
    }
    @FXML
private void handleEdit() {
    Game selected = gameTable.getSelectionModel().getSelectedItem();
 if (selected != null) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/EditGameView.fxml"));
            Parent root = loader.load();

            EditGameController controller = loader.getController();
            controller.setEditingGame(selected);

            Stage stage = new Stage();
            stage.setTitle("Edit Game");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

    @FXML
    private void handleDelete() {
        Game selected = gameTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            gameList.remove(selected);
            try {
                JsonHandler.writeGames(gameList, "games.json");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

@FXML
private void handleClearTagFilter() {
    tagListView.getSelectionModel().clearSelection();
    gameTable.setItems(gameList);
}


    private void showCoverImage(Game game) {
        if (game != null && game.getImagePath() != null) {
            File file = new File(game.getImagePath());
            if (file.exists()) {
                coverPreview.setImage(new Image(file.toURI().toString()));
            } else {
                coverPreview.setImage(null);
            }
        } else {
            coverPreview.setImage(null);
        }
    }
}
