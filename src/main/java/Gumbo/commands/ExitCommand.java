package Gumbo.commands;

import Gumbo.storage.Storage;
import Gumbo.tasks.TaskList;
import Gumbo.ui.Ui;

/**
 * A Command that terminates the application and saves the task lists to storage when executed.
 */
public class ExitCommand extends Command {
    /**
     * Executes the command to exit the application and save the list of tasks in storage.
     * Displays a goodbye message to the user.
     *
     * @param ui User interface to interact with the user.
     * @param storage Storage to save tasks file or load task files from.
     * @param taskList Contains list of tasks.
     */
    @Override
    public void execute(Ui ui, Storage storage, TaskList taskList) {
        try {
            storage.save(taskList);
        } catch (Storage.StorageOperationException e) {
            ui.showError(e.toString());
        }
        ui.showToUser("Bye. Hope to see you again soon!");
        super.terminate = true;
        ui.getScanner().close();
    }
}
