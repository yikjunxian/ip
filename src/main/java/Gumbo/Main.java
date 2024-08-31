import commands.Command;
import exceptions.GumboException;
import parser.Parser;
import storage.Storage;
import tasks.TaskList;
import ui.Ui;



public class Main {

    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    public Main(String filePath) {
        ui = new Ui();
        try {
            storage = new Storage(filePath);
            tasks = new TaskList(storage.load());
        } catch (GumboException e) {
            ui.showLoadingError();
        }
    }

    public void run() {
        ui.showWelcome();
        boolean isExit = false;
        while (!isExit) {
            String fullCommand = ui.readCommand();
            Command c = Parser.parse(fullCommand);
            c.execute(ui, storage, tasks);
            isExit = c.isExit();
        }
    }

    public static void main(String[] args) {
        new Main("data/Gumbo.txt").run();
    }
}
