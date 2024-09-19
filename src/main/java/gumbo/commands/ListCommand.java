package gumbo.commands;

import gumbo.storage.Storage;
import gumbo.tasks.Task;
import gumbo.tasks.TaskList;

/**
 * A Command that lists the tasks in the task list when executed.
 */
public class ListCommand extends Command {

    /**
     * Executes the command to list the tasks in the task list.
     * Displays the list of tasks to the user.
     *
     * @param storage Storage to save tasks file or load task files from.
     * @param taskList Contains list of tasks.
     */
    @Override
    public String execute(Storage storage, TaskList taskList) {
        int i = taskList.size();
        StringBuilder str = new StringBuilder("Here are the tasks in your list:\n");
        for (int j = 1; j < i + 1; j++) {
            Task x = taskList.get(j - 1);
            str.append(j).append(". ").append(x).append("\n");
        }
        return str.toString();
    }
}
