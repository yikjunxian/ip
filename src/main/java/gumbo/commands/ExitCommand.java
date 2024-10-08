package gumbo.commands;

import gumbo.storage.Storage;
import gumbo.tasks.TaskList;

/**
 * A Command that terminates the application and saves the task lists to storage when executed.
 */
public class ExitCommand extends Command {
    /**
     * Executes the command to exit the application and save the list of tasks in storage.
     * Displays a goodbye message to the user.
     *
     * @param storage Storage to save tasks file or load task files from.
     * @param taskList Contains list of tasks.
     */
    @Override
    public String execute(Storage storage, TaskList taskList) {
        try {
            storage.save(taskList);
        } catch (Storage.StorageOperationException e) {
            return e.toString();
        }
        return "Bye. Hope to see you again soon!";
    }
}
