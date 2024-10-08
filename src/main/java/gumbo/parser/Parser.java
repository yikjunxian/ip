package gumbo.parser;

import java.util.ArrayList;
import java.util.List;

import gumbo.commands.AddCommand;
import gumbo.commands.Command;
import gumbo.commands.DeleteCommand;
import gumbo.commands.ExitCommand;
import gumbo.commands.FindCommand;
import gumbo.commands.ListCommand;
import gumbo.commands.MarkCommand;
import gumbo.commands.UnmarkCommand;
import gumbo.commands.UpdateCommand;
import gumbo.exceptions.IllegalValueException;
import gumbo.tasks.Deadline;
import gumbo.tasks.Event;
import gumbo.tasks.Todo;

/**
 * Interprets user input and creates the corresponding {@code Command} objects to execute user instructions.
 */
public class Parser {

    /**
     * Parses the user input and returns the corresponding {@code Command} object.
     *
     * @param fullCommand The full string of user input.
     * @return A {@code Command} object representing the user's request.
     */
    public static Command parse(String fullCommand) throws IllegalValueException {
        String[] commandStr = fullCommand.split(" ");
        return switch(commandStr[0]) {
        case "bye":
            yield new ExitCommand();
        case "list":
            yield new ListCommand();
        case "mark":
            int taskNumMark = Integer.parseInt(commandStr[1]) - 1;
            yield new MarkCommand(taskNumMark);
        case "unmark":
            int taskNum = Integer.parseInt(commandStr[1]) - 1;
            yield new UnmarkCommand(taskNum);
        case "deadline":
            Deadline newDeadline = getDeadline(fullCommand);
            yield new AddCommand(newDeadline);
        case "todo":
            Todo newTodo = getTodo(fullCommand);
            yield new AddCommand(newTodo);
        case "event":
            Event newEvent = getEvent(fullCommand);
            yield new AddCommand(newEvent);
        case "delete":
            int taskNumDelete = Integer.parseInt(commandStr[1]) - 1;
            yield new DeleteCommand(taskNumDelete);
        case "find":
            String keyword = fullCommand.substring(5);
            yield new FindCommand(keyword);
        case "update":
            int taskNumToUpdate = getTaskNumToUpdate(fullCommand);
            String updateDescription = getUpdateDescription(fullCommand);
            yield new UpdateCommand(taskNumToUpdate, updateDescription);
        default:
            throw new IllegalValueException(" OOPS!!! Please specify a task that you would like to add.");
        };
    }

    private static String getUpdateDescription(String userInput) throws IllegalValueException {
        ArrayList<String> descArr = new ArrayList<>(List.of(userInput.split(" ")));
        String detailToUpdate = descArr.get(2);
        boolean isValidDetail = detailToUpdate.equals("description") || detailToUpdate.equals("by")
                || detailToUpdate.equals("from") || detailToUpdate.equals("to");
        if (!isValidDetail) {
            throw new IllegalValueException("Detail to update is not valid. Please specify either 'description', "
                    + "'from' or 'to' to be updated\n" + "E.g. update 1 by 2024-09-14");
        }
        return userInput.substring(9);
    }

    private static int getTaskNumToUpdate(String userInput) throws IllegalValueException {
        String[] inputStr = userInput.split(" ");
        int taskNumToUpdate = Integer.parseInt(inputStr[1]) - 1;
        if (taskNumToUpdate < 0) {
            throw new IllegalValueException("Task number to update is not valid");
        }
        return taskNumToUpdate;
    }

    /**
     * Creates a {@code Todo} object from the user's input.
     * Extracts the description from the input string.
     *
     * @param userInput The full string of user input specifying the deadline.
     * @return A {@code Todo} object representing the user's to-do task.
     * @throws IllegalValueException If the input does not specify a valid description.
     */
    private static Todo getTodo(String userInput) throws IllegalValueException {
        String todoDescription = userInput.substring(4);
        if (todoDescription.isEmpty() || todoDescription.isBlank()) {
            throw new IllegalValueException("OOPS!!! Please include a description of your todo.\n");
        }
        return new Todo(todoDescription);
    }

    /**
     * Creates a {@code Deadline} object from the user's input.
     * Extracts the description and deadline date from the input string.
     *
     * @param userInput The full string of user input specifying the deadline.
     * @return A {@code Deadline} object representing the user's deadline task.
     * @throws IllegalValueException If the input does not specify a valid deadline or date.
     */
    private static Deadline getDeadline(String userInput) throws IllegalValueException {
        int dlIndex = userInput.lastIndexOf("/by");

        // Check description first
        String dlDescription = userInput.substring(8, (dlIndex != -1 ? dlIndex - 1 : userInput.length()));
        if (dlDescription.isEmpty() || dlDescription.isBlank()) {
            throw new IllegalValueException("OOPS!!! Please include a description of your deadline.\n");
        }

        // Now check if the date exists and is valid
        if (dlIndex == -1) {
            throw new IllegalValueException("OOPS!!! Please specify the date of your deadline.\n");
        }

        String deadlineStr = userInput.substring(dlIndex + 4);
        if (deadlineStr.isEmpty() || deadlineStr.isBlank()) {
            throw new IllegalValueException("OOPS!!! Please specify the date of your deadline.\n");
        }

        return new Deadline(dlDescription, deadlineStr);
    }



    /**
     * Creates an {@code Event} object from the user's input.
     * Extracts the description, start time, and end time of the event from the input string.
     *
     * @param userInput The full string of user input specifying the event.
     * @return An {@code Event} object representing the user's event task.
     * @throws IllegalValueException If the input does not specify a valid event description, start time, or end time.
     */
    private static Event getEvent(String userInput) throws IllegalValueException {
        int fromIndex = userInput.lastIndexOf("/from");
        int toIndex = userInput.lastIndexOf("/to");

        // Check the description first
        String eventDescription = userInput.substring(5, (fromIndex != -1 ? fromIndex - 1 : userInput.length()));
        if (eventDescription.isEmpty() || eventDescription.isBlank()) {
            throw new IllegalValueException("OOPS!!! Please include a description of your event.\n");
        }

        // Check the presence of both /from and /to, and validate them after the description
        if (fromIndex == -1 || toIndex == -1) {
            throw new IllegalValueException("OOPS!!! Please specify the start and end date of your event.\n");
        }

        String from = userInput.substring(fromIndex + 6, toIndex - 1);
        String to = userInput.substring(toIndex + 4);

        if (from.isEmpty() || from.isBlank() || to.isEmpty() || to.isBlank()) {
            throw new IllegalValueException("OOPS!!! Please specify the start and end date of your event.\n");
        }

        // Return the event object after validating the description and the dates
        return new Event(eventDescription, from, to);
    }
}
