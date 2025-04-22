package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Game;
import util.JsonHandler;
import java.util.List;
import java.util.stream.Collectors;
import java.io.IOException;

public class GameListController {
    @FXML private TableView<Game> gameTable;
    @FXML private TableColumn<Game, String> titleCol;
    @FXML private TableColumn<Game, String> developerCol;
    @FXML private TableColumn<Game, Integer> releaseYearCol;
    @FXML private TableColumn<Game, Double> ratingCol;
    @FXML private TextField searchField;

    private ObservableList<Game> gameList;

    @FXML
    private void initialize() {
        titleCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getTitle()));
        developerCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getDeveloper()));
        releaseYearCol.setCellValueFactory(data -> new javafx.beans.property.SimpleIntegerProperty(data.getValue().getReleaseYear()).asObject());
        ratingCol.setCellValueFactory(data -> new javafx.beans.property.SimpleDoubleProperty(data.getValue().getRating()).asObject());

        refreshGameList();
    }

    private void refreshGameList() {
        try {
            List<Game> games = JsonHandler.readGames("games.json");
            gameList = FXCollections.observableArrayList(games);
            gameTable.setItems(gameList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSearch() {
        String keyword = searchField.getText().toLowerCase();
        List<Game> filtered = gameList.stream()
            .filter(g -> g.getTitle().toLowerCase().contains(keyword)
                      || g.getDeveloper().toLowerCase().contains(keyword))
            .collect(Collectors.toList());
        gameTable.setItems(FXCollections.observableArrayList(filtered));
    }

    @FXML
    private void handleDelete() {
        Game selected = gameTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Confirm Delete");
            confirm.setHeaderText("Are you sure you want to delete this game?");
            confirm.setContentText("Title: " + selected.getTitle());

            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.NO);
            confirm.getButtonTypes().setAll(yes, no);

            confirm.showAndWait().ifPresent(response -> {
                if (response == yes) {
                    gameList.remove(selected);
                    gameTable.setItems(gameList);
                    try {
                        JsonHandler.writeGames(gameList, "games.json");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

        } else {
            showAlert("Please select a game to delete.");
        }
    }

    @FXML
    private void handleEdit() {
        Game selected = gameTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            EditGameController.setEditingGame(selected);
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/EditGameView.fxml"));
                Parent root = loader.load();
                Stage stage = new Stage();
                stage.setTitle("Edit Game");
                stage.setScene(new Scene(root));
                stage.showAndWait();
                refreshGameList(); // güncelle
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            showAlert("Please select a game to edit.");
        }
    }

    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
