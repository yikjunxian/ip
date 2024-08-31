package commands;

import storage.Storage;
import tasks.TaskList;
import ui.Ui;

public class Command {

    public boolean terminate = false;

    public void execute(Ui ui, Storage storage, TaskList taskList) {
        throw new UnsupportedOperationException("This method is implemented by child classes");
    }

    public boolean isExit() {
        return terminate;
    }
}
