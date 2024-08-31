package commands;

import storage.Storage;
import tasks.Task;
import tasks.TaskList;
import ui.Ui;

public class UnmarkCommand extends Command{
    int taskNumToUnmark;
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
