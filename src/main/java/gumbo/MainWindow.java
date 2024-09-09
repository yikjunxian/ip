package gumbo;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
/**
 * Controller for the main GUI.
 */

// contains the main logic of the main window
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Gumbo gumbo;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/user.jpg"));
    private Image gumboImage = new Image(this.getClass().getResourceAsStream("/images/gumby.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /** Injects the Duke instance */
    public void setGumbo(Gumbo g) {
        gumbo = g;
    }
    public void printWelcome() {
        dialogContainer.getChildren().addAll(
                DialogBox.getGumboDialog("Hello! I'm Gumbo\n"
                        + "What can I do for you?\n", gumboImage)
        );
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = gumbo.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getGumboDialog(response, gumboImage)
        );
        userInput.clear();
    }
}

