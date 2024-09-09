package gumbo.commands;

import gumbo.storage.Storage;
import gumbo.tasks.Task;
import gumbo.tasks.TaskList;
import gumbo.ui.Ui;

/**
 * Represents a command to find tasks that contain a specific keyword.
 */
public class FindCommand extends Command {
    private final String keyword;
    /**
     * Constructs a {@code FindCommand} with the specified keyword.
     *
     * @param keyword The keyword to search for in the task descriptions.
     */
    public FindCommand(String keyword) {
        this.keyword = keyword;
    }
    /**
     * Executes the find command which searches for tasks containing the keyword.
     * Displays all matching tasks to the user.
     *
     * @param ui User interface to interact with the user.
     * @param storage Storage to save tasks file or load task files from.
     * @param taskList Contains list of tasks.
     */
    @Override
    public String execute(Ui ui, Storage storage, TaskList taskList) {
        TaskList matchingTaskList = taskList.findMatchingTasks(this.keyword);
        int i = matchingTaskList.size();
        StringBuilder str = new StringBuilder("Here are tasks matching tasks in your list: \n");
        for (int j = 1; j < i + 1; j++) {
            Task x = matchingTaskList.get(j - 1);
            str.append(j).append(". ").append(x).append("\n");
        }
        return String.valueOf(str);
    }
}
