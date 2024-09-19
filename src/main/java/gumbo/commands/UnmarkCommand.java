package gumbo.commands;

import gumbo.storage.Storage;
import gumbo.tasks.Task;
import gumbo.tasks.TaskList;

/**
 * A command that un-marks a specific task as done.
 */
public class UnmarkCommand extends Command {
    private int taskNumToUnmark;
    /**
     * Constructs a UnmarkCommand with the specified task number to be un-marked.
     *
     * @param taskNum the number of the task to mark as undone
     */
    public UnmarkCommand(int taskNum) {
        this.taskNumToUnmark = taskNum;
    }


    /**
     * Executes the command to un-mark the specified task.
     *
     * @param storage  the storage object that manages tasks
     * @param taskList the list of tasks from which the task will be unmarked
     * @return a confirmation message indicating the task has been unmarked
     */
    @Override
    public String execute(Storage storage, TaskList taskList) {
        Task unmarkedTask = taskList.get(taskNumToUnmark);
        unmarkedTask.markAsUndone();
        return "OK, I've unmarked this task as not done yet:\n"
                + unmarkedTask;
    }
}
