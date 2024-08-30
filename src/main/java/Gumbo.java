import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import java.time.LocalDate;





public class Gumbo {

    // "./" references the current working dir, therefore FILE_PATH search for data dir in the curr dir
    private static final String FILE_PATH = "./data/Gumbo.txt";

    public static void main(String[] args) throws IOException {

        System.out.println("Hello! I'm Gumbo\n" +
                "What can I do for you?\n");

        checkDataDir();
        ArrayList<Task> taskArr = loadTasks();

        Scanner myObj = new Scanner(System.in);  // Create a Scanner object

        while (true) {
            String userInput = myObj.nextLine();  // Read user input
            if (userInput.equals("bye")) {
                saveTasks(taskArr);
                System.out.println("Bye. Hope to see you again soon!");
                break;
            } else if (userInput.equals("list")) {
                int i = taskArr.size();
                System.out.println("Here are the tasks in your list:\n");
                for (int j = 1; j < i + 1; j++) {
                    Task x = taskArr.get(j - 1);
                    System.out.println(j + ". " + x);
                }
            } else if (userInput.startsWith("mark")) {
                int taskNum = Character.getNumericValue(userInput.charAt(5));
                Task completedTask = taskArr.get(taskNum - 1);
                completedTask.markAsDone();
                System.out.println("Nice! I've marked this task as done:\n"
                        + completedTask);
            } else if (userInput.startsWith("unmark")) {
                int taskNum = Character.getNumericValue(userInput.charAt(7));
                Task completedTask = taskArr.get(taskNum - 1);
                completedTask.markAsUndone();
                System.out.println("OK, I've marked this task as not done yet:\n"
                        + completedTask);
            } else if (userInput.startsWith("deadline")) {
                try {
                    Deadline newDeadline = getDeadline(userInput);
                    taskArr.add(newDeadline);
                    System.out.println("Got it. I've added this task:\n"
                            + newDeadline + "\n"
                            + "Now you have " + taskArr.size() + " tasks in the list");
                } catch (GumboException e) {
                    System.out.println(e.getMessage());
                }
            } else if (userInput.startsWith("todo")) {
                try {
                    String todoDescription = userInput.substring(4);
                    if (todoDescription.isEmpty() || todoDescription.isBlank()) {
                        throw new GumboException("OOPS!!! Please include a description of your todo.\n");
                    }
                    Todo newTodo = new Todo(todoDescription);
                    taskArr.add(newTodo);
                    System.out.println("Got it. I've added this task:\n"
                            + newTodo + "\n"
                            + "Now you have " + taskArr.size() + " tasks in the list");
                } catch (GumboException e) {
                    System.out.println(e.getMessage());
                }
            } else if (userInput.startsWith("event")) {
                try {
                    Event newEvent = getEvent(userInput);
                    taskArr.add(newEvent);
                    System.out.println("Got it. I've added this task:\n"
                            + newEvent + "\n"
                            + "Now you have " + taskArr.size() + " tasks in the list");
                } catch (GumboException e) {
                    System.out.println(e.getMessage());
                }
            } else if (userInput.startsWith("delete")) {
                int taskNum = Character.getNumericValue(userInput.charAt(7));
                Task deletedTask = taskArr.get(taskNum - 1);
                taskArr.remove(taskNum - 1);
                System.out.println("Noted. I've removed this task:\n"
                        + deletedTask + "\n"
                        + "Now you have " + taskArr.size() + " tasks in the list");
            } else {
                System.out.println(" OOPS!!! Please specify a task that you would like to add.");
            }
        }
    }

    private static Event getEvent(String userInput) throws GumboException {
        int fromIndex = userInput.lastIndexOf("/from");
        int toIndex = userInput.lastIndexOf("/to");
        if (fromIndex == -1 || toIndex == -1) {
            throw new GumboException("OOPS!!! Please specify the start and end data of your event.\n");
        }
        String from = userInput.substring(fromIndex + 6, toIndex - 1);
        String to = userInput.substring(toIndex + 4);
        if (from.isEmpty() || from.isBlank() || to.isEmpty() || to.isBlank()) {
            throw new GumboException("OOPS!!! Please specify the start and end data of your event.\n");
        }
        String eventDescription = userInput.substring(5, fromIndex - 1);
        if (eventDescription.isEmpty() || eventDescription.isBlank()) {
            throw new GumboException("OOPS!!! Please include a description of your event.\n");
        }
        return new Event(eventDescription,
                userInput.substring(fromIndex + 6, toIndex - 1),
                userInput.substring(toIndex + 4));
    }

    private static Deadline getDeadline(String userInput) throws GumboException {
        int dlIndex = userInput.lastIndexOf("/by");
        String deadlineStr = userInput.substring(dlIndex + 4);
        if (dlIndex == -1 || deadlineStr.isEmpty() || deadlineStr.isBlank()) {
            throw new GumboException("OOPS!!! Please specify the date of your deadline.\n");
        }
        String dlDescription = userInput.substring(8, dlIndex - 1);
        if (dlDescription.isEmpty() || dlDescription.isBlank()) {
            throw new GumboException("OOPS!!! Please include a description of your deadline.\n");
        }
        return new Deadline(dlDescription, deadlineStr);
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

    private static ArrayList<Task> loadTasks() throws IOException {
        ArrayList<Task> taskArr = new ArrayList<>();
        Path path = Paths.get(FILE_PATH);
        if (Files.exists(path)) {
            try {
                List<String> taskTexts = Files.readAllLines(path);
                for (String taskText : taskTexts) {
                    Task newTask = textToTask(taskText);
                    taskArr.add(newTask);
                }
            } catch (IOException e) {
                System.out.println("Task could not be loaded" + e.getMessage());
            }
        } else {
            System.out.println("No previous task created");
        }
        return taskArr;

    }

    private static void saveTasks(ArrayList<Task> taskArr) throws IOException {
        Path path = Paths.get(FILE_PATH);
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (Task task : taskArr) {
                writer.write(task.toTextString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Tasks could not be saved:" + e.getMessage());
        }
    }

    private static void checkDataDir() throws IOException {
        Path path = Paths.get("./data");
        if (Files.notExists(path)) {
            try {
                Files.createDirectory(path);
            } catch (IOException e) {
                System.out.println("Could not create data directory:" + e.getMessage());
            }
        }
    }
}
