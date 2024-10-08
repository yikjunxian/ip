package gumbo.commands;

import gumbo.storage.Storage;
import gumbo.tasks.Task;
import gumbo.tasks.TaskList;

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
     * @param storage Storage to save tasks file or load task files from.
     * @param taskList Contains list of tasks.
     */
    @Override
    public String execute(Storage storage, TaskList taskList) {
        assert taskToAdd != null : "Task to add is not available";
        assert taskList != null : "Task list is not available";
        taskList.add(taskToAdd);
        return "Got it. I've added this task:\n"
                + taskToAdd + "\n"
                + "Now you have " + taskList.size() + " tasks in the list";
    }
}
