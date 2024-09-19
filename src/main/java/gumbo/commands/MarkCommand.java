package gumbo.commands;

import gumbo.storage.Storage;
import gumbo.tasks.Task;
import gumbo.tasks.TaskList;

/**
 * A command that marks a specific task as done.
 */
public class MarkCommand extends Command {
    private int taskNumToMark;

    /**
     * Constructs a MarkCommand with the specified task number to be marked.
     *
     * @param taskNum the number of the task to mark as done
     */
    public MarkCommand(int taskNum) {
        this.taskNumToMark = taskNum;
    }

    /**
     * Executes the command to mark the specified task as done.
     *
     * @param storage  the storage object that manages tasks
     * @param taskList the list of tasks from which the task will be marked as done
     * @return a confirmation message indicating the task has been marked as done
     */
    @Override
    public String execute(Storage storage, TaskList taskList) {
        Task markedTask = taskList.get(taskNumToMark);
        markedTask.markAsDone();
        return "Nice! I've marked this task as done:\n"
                + markedTask;
    }
}
