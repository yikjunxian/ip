package Gumbo.commands;

import Gumbo.storage.Storage;
import Gumbo.tasks.Task;
import Gumbo.tasks.TaskList;
import Gumbo.ui.Ui;

/**
 * A Command that lists the tasks in the task list when executed.
 */
public class ListCommand extends Command {

    /**
     * Executes the command to list the tasks in the task list.
     * Displays the list of tasks to the user.
     *
     * @param ui User interface to interact with the user.
     * @param storage Storage to save tasks file or load task files from.
     * @param taskList Contains list of tasks.
     */
    @Override
    public void execute(Ui ui, Storage storage, TaskList taskList) {
        int i = taskList.size();
        ui.showToUser("Here are the tasks in your list:\n");
        for (int j = 1; j < i + 1; j++) {
            Task x = taskList.get(j - 1);
            ui.showToUser(j + ". " + x);
        }
    }
}
