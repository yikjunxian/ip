package gumbo.commands;

import gumbo.exceptions.IllegalValueException;
import gumbo.storage.Storage;
import gumbo.tasks.TaskList;

/**
 * Represents an executable command.
 */
public class Command {

    /**
     * Executes the command and returns the result.
     * This method should be overridden by subclasses to provide different implementation of specific commands
     *
     * @param storage Storage to save tasks file or load task files from.
     * @param taskList Contains list of tasks.
     */
    public String execute(Storage storage, TaskList taskList) throws UnsupportedOperationException, IllegalValueException {
        throw new UnsupportedOperationException("This method is implemented by child classes");
    }
}
