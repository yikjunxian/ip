package gumbo;

import gumbo.commands.Command;
import gumbo.exceptions.GumboException;
import gumbo.parser.Parser;
import gumbo.storage.Storage;
import gumbo.tasks.TaskList;
import gumbo.ui.Ui;


/**
 * Entry point of the Gumbo chatbot application.
 */
public class Main {

    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    /**
     * Constructs a {@code Main} instance with the relevant storage file path.
     * Initialises the ui, storage and tasks fields.
     *
     * @param filePath File path for loading and saving tasks data.
     */
    public Main(String filePath) {
        ui = new Ui();
        try {
            storage = new Storage(filePath);
            tasks = new TaskList(storage.load());
        } catch (GumboException e) {
            ui.showLoadingError();
        }
    }

    /**
     * Executes the main logic of the application.
     */
    public void run() {
        ui.showWelcome();
        boolean isExit = false;
        while (!isExit) {
            String fullCommand = ui.readCommand();
            Command c = Parser.parse(fullCommand);
            c.execute(ui, storage, tasks);
            isExit = c.isExit();
        }
    }

    /**
     * Main entry point of the application.
     * Instantiates a {@code Main} instance with the relevant file path.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        new Main("data/gumbo.txt").run();
    }
}
