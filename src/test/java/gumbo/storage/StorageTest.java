package gumbo.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gumbo.tasks.Task;
import gumbo.tasks.Todo;
public class StorageTest {

    private static final Path TEST_FILE_PATH = Paths.get("testGumbo.txt");
    private Storage storage;

    @BeforeEach
    public void setUp() throws Storage.InvalidStorageFilePathException {
        storage = new Storage(TEST_FILE_PATH.toString());
    }

    @AfterEach
    public void tearDown() throws IOException {
        Files.deleteIfExists(TEST_FILE_PATH);
    }

    @Test
    public void load_nonExistentFile_emptyListReturned() throws Storage.StorageOperationException {
        // No file is created here, so the file does not exist
        ArrayList<Task> tasks = storage.load();
        assertTrue(tasks.isEmpty());
    }

    @Test
    public void load_existingFileWithValidTasks_tasksLoadedCorrectly() throws IOException,
            Storage.StorageOperationException {
        // Prepare a file with valid task data
        String content = "T,0,test task";
        Files.write(TEST_FILE_PATH, content.getBytes());

        ArrayList<Task> tasks = storage.load();

        assertInstanceOf(Todo.class, tasks.get(0));
    }

    @Test
    public void load_existingFileWithInvalidData_exceptionThrown() throws IOException {
        // Prepare a file with invalid task data
        String invalidData = "X,0,Invalid task format";
        Files.write(TEST_FILE_PATH, invalidData.getBytes());

        try {
            storage.load();

        } catch (Storage.StorageOperationException e) {
            assertEquals("Error writing to file: " + TEST_FILE_PATH, e.getMessage());
        }

    }
}
