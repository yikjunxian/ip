package gumbo.commands;

import gumbo.storage.Storage;
import gumbo.tasks.Task;
import gumbo.tasks.TaskList;
import gumbo.ui.Ui;

/**
 * A Command that deletes a task to the task list when executed.
 */
public class DeleteCommand extends Command {
    private int taskNumToDelete;

    /**
     * Constructs an A{@code DeleteCommand} with the specified task to be added.
     *
     * @param taskNumToDelete Task index to be deleted from the task list.
     */
    public DeleteCommand(int taskNumToDelete) {
        this.taskNumToDelete = taskNumToDelete;
    }

    /**
     * Executes the command to delete the specified task to the task list.
     * Displays a message to the user indicating the task deleted and shows the current number of tasks.
     *
     * @param ui User interface to interact with the user.
     * @param storage Storage to save tasks file or load task files from.
     * @param taskList Contains list of tasks.
     */
    @Override
    public void execute(Ui ui, Storage storage, TaskList taskList) {
        Task deletedTask = taskList.get(taskNumToDelete);
        taskList.remove(taskNumToDelete);
        ui.showToUser("Noted. I've removed this task:\n"
                + deletedTask + "\n"
                + "Now you have " + taskList.size() + " tasks in the list");
    }
}
