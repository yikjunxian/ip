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
            ui.showLoadingError();
        }
    }

    public String getResponse(String input) {
        assert storage != null : "Storage not available";
        assert tasks != null : "Task list not available";
        assert ui != null : "UI not available";

        try {
            Command c = Parser.parse(input);
            return c.execute(ui, storage, tasks);

        } catch (IllegalValueException e) {
            return e.getMessage();
        }
    }
}
