package Gumbo.commands;

import Gumbo.storage.Storage;
import Gumbo.tasks.Task;
import Gumbo.tasks.TaskList;
import Gumbo.ui.Ui;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class AddCommandTest {
    private TaskList tasklist;
    private Ui ui;
    private Storage storage;

    @Test
    public void execute_nonEmptyTask_success() throws Exception {
        tasklist = new TaskList(new ArrayList<Task>());
        ui = new Ui();
        storage = new Storage("data/Gumbo.txt");
        Task testTask = new Task("generic task");
        AddCommand addCommand = new AddCommand(testTask);

        addCommand.execute(ui, storage, tasklist);
        // adding one task results tasklist increasing in size by 1
        assertEquals(1, tasklist.size());

        // task added to tasklist is same as task used to initialise AddCommand
        assertEquals(testTask, tasklist.get(0));
    }

    @Test
    public void execute_outputMessage_success() throws Exception {
        tasklist = new TaskList(new ArrayList<Task>());
        ui = new Ui();
        storage = new Storage("data/Gumbo.txt");
        Task testTask = new Task("generic task");
        AddCommand addCommand = new AddCommand(testTask);

        // Capture the standard output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out; // Save the original System.out
        System.setOut(new PrintStream(outputStream));

        try {
            // Act
            addCommand.execute(ui, storage, tasklist);
        } finally {
            // Restore the original System.out
            System.setOut(originalOut);
        }

        // Assert the output
        String expectedOutput = "Got it. I've added this task:\n"
                + testTask + "\n"
                + "Now you have 1 tasks in the list\n";  // Make sure to match the exact format including new lines
        assertEquals(expectedOutput.trim(), outputStream.toString().trim());
    }
}
