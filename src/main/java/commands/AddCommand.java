package commands;

import storage.Storage;
import tasks.Task;
import tasks.TaskList;
import ui.Ui;

public class AddCommand extends Command {
    Task taskToAdd;
    public AddCommand(Task taskToAdd) {
        this.taskToAdd = taskToAdd;
    }

    @Override
    public void execute(Ui ui, Storage storage, TaskList taskList) {
        taskList.add(taskToAdd);
        ui.showToUser("Got it. I've added this task:\n"
                + taskToAdd + "\n"
                + "Now you have " + taskList.size() + " tasks in the list");
    }
}
