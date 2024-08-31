package commands;

import storage.Storage;
import tasks.Task;
import tasks.TaskList;
import ui.Ui;

public class DeleteCommand extends Command {
    public int taskNumToDelete;

    public DeleteCommand(int taskNumToDelete) {
        this.taskNumToDelete = taskNumToDelete;
    }

    @Override
    public void execute(Ui ui, Storage storage, TaskList taskList) {
        Task deletedTask = taskList.get(taskNumToDelete);
        taskList.remove(taskNumToDelete);
        ui.showToUser("Noted. I've removed this task:\n"
                + deletedTask + "\n"
                + "Now you have " + taskList.size() + " tasks in the list");
    }
}
