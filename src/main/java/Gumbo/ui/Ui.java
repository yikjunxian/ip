package Gumbo.ui;
import java.util.Scanner;

public class Ui {

    public Scanner scanner;

    public void showWelcome() {
        System.out.println("Hello! I'm Gumbo\n"
                + "What can I do for you?\n");
    }
    public String readCommand() {
        // open scanner to take user input
        String fullCommand = "";
        scanner = new Scanner(System.in);
        if (scanner.hasNextLine()) {
            fullCommand = scanner.nextLine();
        }
        return fullCommand;
    }

    public void showToUser(String msg) {
        System.out.println(msg);
    }

    public void showError(String errorMessage) {
        System.out.println(errorMessage);
    }

    public void showLoadingError() {
        System.out.println("Failed to initialise tasks from file.\n"
                + "New task list created");
    }
}
