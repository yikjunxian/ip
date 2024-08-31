package commands;

import storage.Storage;
import tasks.Task;
import tasks.TaskList;
import ui.Ui;

public class MarkCommand extends Command{
    int taskNumToMark;
    public MarkCommand(int taskNum) {
        this.taskNumToMark = taskNum;
    }

    @Override
    public void execute(Ui ui, Storage storage, TaskList taskList) {
        Task markedTask = taskList.get(taskNumToMark);
        markedTask.markAsDone();
        ui.showToUser("Nice! I've marked this task as done:\n"
                + markedTask);
    }
}
