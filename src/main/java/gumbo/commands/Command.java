package gumbo.commands;

import gumbo.storage.Storage;
import gumbo.tasks.TaskList;
import gumbo.ui.Ui;

/**
 * Represents an executable command.
 */
public class Command {

    private boolean shouldTerminate = false;

    /**
     * Executes the command and returns the result.
     * This method should be overridden by subclasses to provide different implementation of specific commands
     *
     * @param ui User interface to interact with the user.
     * @param storage Storage to save tasks file or load task files from.
     * @param taskList Contains list of tasks.
     */
    public void execute(Ui ui, Storage storage, TaskList taskList) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("This method is implemented by child classes");
    }

    /**
     * Checks if command is an exit command.
     *
     * @return {@code true} if command should terminate the application, {@code false} otherwise.
     */
    public boolean isExit() {
        return shouldTerminate;
    }
    public void setTerminateTrue() {
        this.shouldTerminate = true;
    }
}
