package Gumbo.storage;

import Gumbo.exceptions.GumboException;
import Gumbo.exceptions.IllegalValueException;
import Gumbo.tasks.Deadline;
import Gumbo.tasks.Event;
import Gumbo.tasks.Task;
import Gumbo.tasks.TaskList;
import Gumbo.tasks.Todo;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

//  deals with loading tasks from the file and saving tasks in the file
public class Storage {

    /** Default file path used if the user doesn't provide the file name. */
    public static final String DEFAULT_STORAGE_FILEPATH = "Gumbo.txt";

    public final Path path;

    public Storage() throws InvalidStorageFilePathException {
        this(DEFAULT_STORAGE_FILEPATH);
    }

    /**
     * @throws InvalidStorageFilePathException if the given file path is invalid
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
     */
    private static boolean isValidPath(Path filePath) {
        return filePath.toString().endsWith(".txt");
    }

    /**
     * Saves the tasks data to file.
     *
     * @throws StorageOperationException if there were errors converting and/or storing data to file.
     */
    public void save(TaskList taskList) throws StorageOperationException {
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
     * @throws StorageOperationException if there were errors reading and/or converting data from file.
     */
    public ArrayList<Task> load() throws StorageOperationException {
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
    private static Task textToTask(String text) {
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
