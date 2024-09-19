package gumbo.storage;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import gumbo.exceptions.GumboException;
import gumbo.exceptions.IllegalValueException;
import gumbo.tasks.Deadline;
import gumbo.tasks.Event;
import gumbo.tasks.Task;
import gumbo.tasks.TaskList;
import gumbo.tasks.Todo;


/**
 * Handles the loading and saving of tasks to a file.
 */
public class Storage {

    /** Default file path used if the user doesn't provide the file name. */
    public static final String DEFAULT_STORAGE_FILEPATH = "Gumbo.txt";

    public final Path path;

    /**
     * Constructs a {@code Storage} object with the default file path when no file path is specified.
     *
     * @throws InvalidStorageFilePathException if the default file path is invalid.
     */
    public Storage() throws InvalidStorageFilePathException {
        this(DEFAULT_STORAGE_FILEPATH);
    }

    /**
     * Constructs a {@code Storage} object with the specified file path.
     *
     * @param filePath The path to the file where tasks will be stored.
     * @throws InvalidStorageFilePathException if the given file path is invalid.
     */
    public Storage(String filePath) throws InvalidStorageFilePathException {
        path = Paths.get(filePath);
        Path dirPath = Paths.get("./data");

        if (Files.notExists(dirPath)) {
            try {
                Files.createDirectory(dirPath);
            } catch (IOException e) {
                throw new InvalidStorageFilePathException("Could not create data directory:" + e.getMessage());
            }
        }

        if (!isValidPath(path)) {
            throw new InvalidStorageFilePathException("Storage file should end with '.txt'");
        }
    }

    /**
     * Returns true if the given path is acceptable as a storage file.
     * The file path is considered acceptable if it ends with '.txt'
     *
     * @param filePath The path to validate.
     * @return {@code true} if the path ends with '.txt', {@code false} otherwise.
     */
    private static boolean isValidPath(Path filePath) {
        return filePath.toString().endsWith(".txt");
    }

    /**
     * Saves the tasks data to file.
     *
     * @param taskList The {@code TaskList} containing tasks to save.
     * @throws StorageOperationException if there were errors converting and/or storing data to file.
     */
    public void save(TaskList taskList) throws StorageOperationException {
        assert taskList != null : "Task list is not valid";
        try (BufferedWriter writer = Files.newBufferedWriter(path);
        ) {
            ArrayList<Task> taskArr = taskList.getAll();
            for (Task task : taskArr) {
                writer.write(task.toTextString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing to file: " + path);
        }
    }

    /**
     * Loads the task data from file, and then returns it.
     * Returns an empty  if the file does not exist, or is not a regular file.
     *
     * @return An {@code ArrayList} of tasks loaded from the file.
     * @throws StorageOperationException if there were errors reading and/or converting data from file.
     */
    public ArrayList<Task> load() throws StorageOperationException, IllegalValueException {
        ArrayList<Task> taskArr = new ArrayList<>();

        if (!Files.exists(path) || !Files.isRegularFile(path)) {
            return taskArr;
        }

        try {
            List<String> taskTexts = Files.readAllLines(path);
            System.out.println("loading");
            for (String taskText : taskTexts) {
                Task newTask = textToTask(taskText);
                taskArr.add(newTask);
                System.out.println("adding");
            }
        } catch (FileNotFoundException fnfe) {
            throw new AssertionError("A non-existent file scenario is already handled earlier.");
            // other errors
        } catch (IOException ioe) {
            throw new StorageOperationException("Error writing to file: " + path);
        }
        return taskArr;
    }

    /**
     * Converts a text line from the file to a {@code Task} object.
     *
     * @param text The text line representing a task.
     * @return A {@code Task} object created from the text line.
     */
    private static Task textToTask(String text) throws IllegalValueException {
        String[] str = text.split(",");
        Task newTask = switch (str[0]) {
        case "D" -> new Deadline(str[2], str[3]);
        case "T" -> new Todo(str[2]);
        case "E" -> new Event(str[2], str[3], str[4]);
        default -> new Task(text);
        };
        if (str[1].equals("1")) {
            newTask.markAsDone();
        }
        return newTask;
    }

    public String getPath() {
        return path.toString();
    }

    /* Note: Note the use of nested classes below.
     * More info https://docs.oracle.com/javase/tutorial/java/javaOO/nested.html
     */

    /**
     * Signals that the given file path does not fulfill the storage filepath constraints.
     */
    public static class InvalidStorageFilePathException extends IllegalValueException {
        public InvalidStorageFilePathException(String message) {
            super(message);
        }
    }

    /**
     * Signals that some error has occured while trying to convert and read/write data between the application
     * and the storage file.
     */
    public static class StorageOperationException extends GumboException {
        public StorageOperationException(String message) {
            super(message);
        }
    }
}
