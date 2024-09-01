package Gumbo.ui;
import java.util.Scanner;


/**
 * Handles all interactions with the user.
 */
public class Ui {

    private Scanner scanner;

    /**
     * Displays a welcome message to the user when the application starts.
     */
    public void showWelcome() {
        System.out.println("Hello! I'm Gumbo\n"
                + "What can I do for you?\n");
    }
    /**
     * Reads the full command entered by the user from the console.
     *
     * @return The full command entered by the user.
     */
    public String readCommand() {
        // open scanner to take user input
        String fullCommand = "";
        scanner = new Scanner(System.in);
        if (scanner.hasNextLine()) {
            fullCommand = scanner.nextLine();
        }
        return fullCommand;
    }

    /**
     * Displays a specified message to the user.
     *
     * @param msg The message to be displayed to the user.
     */
    public void showToUser(String msg) {
        System.out.println(msg);
    }

    /**
     * Displays an error message to the user.
     *
     * @param errorMessage The error message to be displayed.
     */
    public void showError(String errorMessage) {
        System.out.println(errorMessage);
    }

    /**
     * Displays an error message indicating that the tasks could not be loaded
     * from the file and that a new task list has been created.
     */
    public void showLoadingError() {
        System.out.println("Failed to initialise tasks from file.\n"
                + "New task list created");
    }
    public Scanner getScanner() {
        return scanner;
    }
}
