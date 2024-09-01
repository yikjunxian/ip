package gumbo.commands;

import gumbo.storage.Storage;
import gumbo.tasks.Task;
import gumbo.tasks.TaskList;
import gumbo.ui.Ui;

/**
 * A Command that adds a task to the task list when executed.
 */
public class AddCommand extends Command {
    private Task taskToAdd;

    /**
     * Constructs an A{@code AddCommand} with the specified task to be added.
     *
     * @param taskToAdd Task to be added to the task list.
     */
    public AddCommand(Task taskToAdd) {
        this.taskToAdd = taskToAdd;
    }

    /**
     * Executes the command to add the specified task to the task list.
     * Displays a message to the user indicating the task added and shows the current number of tasks.
     *
     * @param ui User interface to interact with the user.
     * @param storage Storage to save tasks file or load task files from.
     * @param taskList Contains list of tasks.
     */
    @Override
    public void execute(Ui ui, Storage storage, TaskList taskList) {
        taskList.add(taskToAdd);
        ui.showToUser("Got it. I've added this task:\n"
                + taskToAdd + "\n"
                + "Now you have " + taskList.size() + " tasks in the list");
    }
}
