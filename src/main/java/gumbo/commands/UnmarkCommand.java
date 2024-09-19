package gumbo.commands;

import gumbo.storage.Storage;
import gumbo.tasks.Task;
import gumbo.tasks.TaskList;

public class UnmarkCommand extends Command {
    private int taskNumToUnmark;
    public UnmarkCommand(int taskNum) {
        this.taskNumToUnmark = taskNum;
    }

    @Override
    public String execute(Storage storage, TaskList taskList) {
        Task unmarkedTask = taskList.get(taskNumToUnmark);
        unmarkedTask.markAsUndone();
        return "OK, I've unmarked this task as not done yet:\n"
                + unmarkedTask;
    }
}
