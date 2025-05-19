package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Game;

import java.io.File;

public class GameDetailsController {

    @FXML private Label titleLabel;
    @FXML private Label developerLabel;
    @FXML private Label releaseYearLabel;
    @FXML private Label platformsLabel;
    @FXML private Label tagsLabel;
    @FXML private Label ratingStarsLabel;
    @FXML private ImageView coverImageView;

    public void setGame(Game game) {
        titleLabel.setText(game.getTitle());
        developerLabel.setText(game.getDeveloper());
        releaseYearLabel.setText(String.valueOf(game.getReleaseYear()));
        platformsLabel.setText(String.join(", ", game.getPlatforms()));
        tagsLabel.setText(String.join(", ", game.getTags()));
        ratingStarsLabel.setText(getStarRating(game.getRating()));

        if (game.getImagePath() != null) {
            File imageFile = new File(game.getImagePath());
            if (imageFile.exists()) {
                coverImageView.setImage(new Image(imageFile.toURI().toString()));
                coverImageView.setVisible(true);
            } else {
                coverImageView.setImage(null);
                coverImageView.setVisible(false);
            }
        } else {
            coverImageView.setImage(null);
            coverImageView.setVisible(false);
        }
    }

    private String getStarRating(double ratingOutOf10) {
        int stars = (int) ratingOutOf10; // sadece tam sayı kısmı alınır
        StringBuilder starString = new StringBuilder();
        for (int i = 0; i < stars; i++) starString.append("★");
        for (int i = stars; i < 10; i++) starString.append("☆");
        return starString.toString();
    }
}
    