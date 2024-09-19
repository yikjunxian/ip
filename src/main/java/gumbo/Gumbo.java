package gumbo;

import gumbo.commands.Command;
import gumbo.exceptions.GumboException;
import gumbo.exceptions.IllegalValueException;
import gumbo.parser.Parser;
import gumbo.storage.Storage;
import gumbo.tasks.TaskList;
import gumbo.ui.Ui;

/**
 * Entry point of the Gumbo chatbot application.
 */
public class Gumbo {
    private Storage storage;
    private TaskList tasks;
    private Ui ui;
    private String errorMsg = "";
    /**
     * Constructs a {@code Main} instance with the relevant storage file path.
     * Initialises the ui, storage and tasks fields.
     *
     * @param filePath File path for loading and saving tasks data.
     */
    public Gumbo(String filePath) {
        ui = new Ui();
        try {
            storage = new Storage(filePath);
            tasks = new TaskList(storage.load());
        } catch (GumboException e) {
            errorMsg = e.getMessage();
        }
    }

    /**
     * Parses and executes user commands and returns the response of the chatbot.
     * This method returns the relevant error message if there was an exception thrown
     * when initialising the storage or loading the task list.
     *
     * @param input The full String of user input
     * @return a String that contains the response of the chatbot based on user input
     */
    public String getResponse(String input) {
        assert storage != null : "Storage not available";
        assert tasks != null : "Task list not available";
        assert ui != null : "UI not available";

        if (!errorMsg.isEmpty()) {
            return errorMsg;
        }
        try {
            Command c = Parser.parse(input);
            return c.execute(ui, storage, tasks);
        } catch (IllegalValueException e) {
            return e.getMessage();
        }
    }
}
