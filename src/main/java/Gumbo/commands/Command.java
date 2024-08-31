package Gumbo.commands;

import Gumbo.storage.Storage;
import Gumbo.tasks.TaskList;
import Gumbo.ui.Ui;

public class Command {

    public boolean terminate = false;

    public void execute(Ui ui, Storage storage, TaskList taskList) {
        throw new UnsupportedOperationException("This method is implemented by child classes");
    }

    public boolean isExit() {
        return terminate;
    }
}
