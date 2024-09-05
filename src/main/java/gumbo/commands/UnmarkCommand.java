package gumbo.commands;

import gumbo.storage.Storage;
import gumbo.tasks.Task;
import gumbo.tasks.TaskList;
import gumbo.ui.Ui;

public class UnmarkCommand extends Command {
    private int taskNumToUnmark;
    public UnmarkCommand(int taskNum) {
        this.taskNumToUnmark = taskNum;
    }

    @Override
    public void execute(Ui ui, Storage storage, TaskList taskList) {
        Task unmarkedTask = taskList.get(taskNumToUnmark);
        unmarkedTask.markAsUndone();
        ui.showToUser("OK, I've unmarked this task as not done yet:\n"
                + unmarkedTask);
    }
}
