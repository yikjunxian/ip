package Gumbo.commands;

import Gumbo.storage.Storage;
import Gumbo.tasks.Task;
import Gumbo.tasks.TaskList;
import Gumbo.ui.Ui;

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
