package Gumbo.commands;

import Gumbo.storage.Storage;
import Gumbo.tasks.TaskList;
import Gumbo.ui.Ui;

public class ExitCommand extends Command{
    @Override
    public void execute(Ui ui, Storage storage, TaskList taskList) {
        try {
            storage.save(taskList);
        } catch (Storage.StorageOperationException e) {
            ui.showError(e.toString());
        }
        ui.showToUser("Bye. Hope to see you again soon!");
        super.terminate = true;
        ui.scanner.close();
    }
}
