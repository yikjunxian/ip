package gumbo;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
/**
 * A GUI for Duke using FXML.
 */
public class Main extends Application {
    private Gumbo gumbo = new Gumbo("data/Gumbo.txt");
    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            /*
            When load() is called:
            1. loads the FXML file (MainWindow.fxml)
            2. Creates an instance of the controller class specified by the fx:controller attribute,
                by calling its no-argument constructor (new MainWindow())
            3. Sets the value of any @FXML-annotated fields in the controller to the elements defined
                with matching fx:id attributes
                (e.g assigns scrollPane var in MainWindow to the ScrollPane in the MainWindow.fxml)
            4. Registers any event handlers mapping to methods in the controller (handleUserInput)
            5. Calls the initialize() method on the controller, if there is one.
            */
            AnchorPane ap = fxmlLoader.load(); // loads object hierachy from fxml document
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            // get controller returns the controller associated with the root obj (with is main window)
            fxmlLoader.<MainWindow>getController().setGumbo(gumbo); // inject the Duke instance
            fxmlLoader.<MainWindow>getController().printWelcome(); // inject the Duke instance
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
