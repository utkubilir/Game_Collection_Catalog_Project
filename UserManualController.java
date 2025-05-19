package controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class UserManualController {

    @FXML private TextArea manualTextArea;

    @FXML
    private void initialize() {
        manualTextArea.setText("""
        Game Collection Catalog - User Manual

        Adding a New Game:
        - Click the "Add New Game" button.
        - Fill in all required fields: Title, Developer, Publisher, Steam ID, Genre, Platforms, Translators, Release Year, Format, Language, Rating, Tags.
        - Steam ID must be a numeric value.
        - Rating must be between 0 and 10.
        - Playtime and cover image are optional.

        Game List:
        - View all games in a table.
        - Sort by clicking column headers (e.g., Rating, Release Year).
        - Double-click a row to open the game details window.

        Filtering Games:
        - Use the search bar to filter games by title, developer, publisher, etc.
        - Select one or more tags to filter games by tags.
        - Only games that contain all selected tags will be displayed.
        - Hold the Shift key to select multiple tags.

        Editing and Deleting Games:
        - Select a game in the list.
        - Click "Edit" to modify its details.
        - Click "Delete" to remove it from the catalog.
        - To delete a image of a game , in the edit section path of the image needs to be deleted.

        Importing and Exporting:
        - Use the "File" menu to import/export game data in JSON format.

        Cover Images:
        - You can upload images in JPG, JPEG, or PNG format.
        - The image is displayed in the details view if provided.

        For further support, please contact the developer.
        """);
    }
}
