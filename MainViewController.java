package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

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
    private void handleExit() {
        System.exit(0);
    }

    @FXML
    private void handleHelp() {
        // Add help view if needed
    }
}