package gumbo.commands;

import gumbo.exceptions.IllegalValueException;
import gumbo.storage.Storage;
import gumbo.tasks.Deadline;
import gumbo.tasks.Event;
import gumbo.tasks.Task;
import gumbo.tasks.TaskList;
import gumbo.ui.Ui;

/**
 * A Command that updates a task to the task list when executed.
 */
public class UpdateCommand extends Command {
    private int taskNumToUpdate;
    private String updatedDesc;

    /**
     * Constructs an A{@code UpdateCommand} with the specified task to be added.
     *
     * @param taskNumToUpdate Task index to be deleted from the task list.
     */
    public UpdateCommand(int taskNumToUpdate, String updatedDesc) throws IllegalValueException {
        this.taskNumToUpdate = taskNumToUpdate;
        this.updatedDesc = updatedDesc;
    }

    /**
     * Executes the command to delete the specified task to the task list.
     * Displays a message to the user indicating the task deleted and shows the current number of tasks.
     *
     * @param ui User interface to interact with the user.
     * @param storage Storage to save tasks file or load task files from.
     * @param taskList Contains list of tasks.
     */
    @Override
    public String execute(Ui ui, Storage storage, TaskList taskList) throws IllegalValueException {
        Task taskToUpdate = taskList.get(taskNumToUpdate);
        String[] updateDescArr = updatedDesc.split(" ");
        if (updateDescArr[0].equals("description")) {
            taskToUpdate.updateDesc(updatedDesc.substring(12));
        } else if ((taskToUpdate instanceof Deadline deadline) && (updateDescArr[0].equals("by"))) {
            deadline.updateDeadline(updateDescArr[1]);
        } else if ((taskToUpdate instanceof Event event) && (updateDescArr[0].equals("from"))) {
            event.updateFrom(updateDescArr[1]);
        } else if ((taskToUpdate instanceof Event event) && (updateDescArr[0].equals("to"))) {
            event.updateTo(updateDescArr[1]);
        } else {
            throw new IllegalValueException("Invalid task detail to update. "
                    + "Please ensure detail matches type of task.");
        }
        return "Got it. I've updated this task:\n" + taskToUpdate;
    }
}
