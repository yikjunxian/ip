package gumbo.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import gumbo.tasks.Todo;
import gumbo.storage.Storage;
import gumbo.tasks.Task;
import gumbo.tasks.TaskList;


public class AddCommandTest {
    private TaskList tasklist;
    private Storage storage;

    @Test
    public void execute_nonEmptyTask_success() throws Exception {
        tasklist = new TaskList(new ArrayList<Task>());
        storage = new Storage("data/Gumbo.txt");
        Task testTask = new Task("generic task");
        AddCommand addCommand = new AddCommand(testTask);

        addCommand.execute(storage, tasklist);
        // adding one task results tasklist increasing in size by 1
        assertEquals(1, tasklist.size());

        // task added to tasklist is same as task used to initialise AddCommand
        assertEquals(testTask, tasklist.get(0));
    }

    @Test
    public void execute_outputMessage_success() throws Exception {
        tasklist = new TaskList(new ArrayList<Task>());
        storage = new Storage("data/Gumbo.txt");
        Todo testTask = new Todo("test");
        AddCommand addCommand = new AddCommand(testTask);

        // Capture the standard output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out; // Save the original System.out
        System.setOut(new PrintStream(outputStream));

        String outputString = addCommand.execute(storage, tasklist);

        // Assert the output
        String expectedOutput = "Got it. I've added this task:\n"
                + testTask + "\n"
                + "Now you have 1 tasks in the list";  // Make sure to match the exact format including new lines
        assertEquals(expectedOutput, outputString);
    }
}


