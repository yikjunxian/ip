package gumbo.parser;

import gumbo.commands.AddCommand;
import gumbo.commands.Command;
import gumbo.commands.DeleteCommand;
import gumbo.commands.ExitCommand;
import gumbo.commands.ListCommand;
import gumbo.commands.MarkCommand;
import gumbo.commands.UnmarkCommand;
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
    public static Command parse(String fullCommand) {
        if (fullCommand.equals("bye")) {
            return new ExitCommand();

        } else if (fullCommand.equals("list")) {
            return new ListCommand();

        } else if (fullCommand.startsWith("mark")) {
            int taskNum = Character.getNumericValue(fullCommand.charAt(5));
            return new MarkCommand(taskNum - 1);

        } else if (fullCommand.startsWith("unmark")) {
            int taskNum = Character.getNumericValue(fullCommand.charAt(7));
            return new UnmarkCommand(taskNum - 1);

        } else if (fullCommand.startsWith("deadline")) {
            try {
                Deadline newDeadline = getDeadline(fullCommand);
                return new AddCommand(newDeadline);
            } catch (IllegalValueException e) {
                System.out.println(e.getMessage());
                return new Command();
            }

        } else if (fullCommand.startsWith("todo")) {
            try {
                String todoDescription = fullCommand.substring(4);
                if (todoDescription.isEmpty() || todoDescription.isBlank()) {
                    throw new IllegalValueException("OOPS!!! Please include a description of your todo.\n");
                }
                Todo newTodo = new Todo(todoDescription);
                return new AddCommand(newTodo);
            } catch (IllegalValueException e) {
                System.out.println(e.getMessage());
                return new Command();
            }
        } else if (fullCommand.startsWith("event")) {
            try {
                Event newEvent = getEvent(fullCommand);
                return new AddCommand(newEvent);
            } catch (IllegalValueException e) {
                System.out.println(e.getMessage());
                return new Command();
            }
        } else if (fullCommand.startsWith("delete")) {
            int taskNum = Character.getNumericValue(fullCommand.charAt(7));
            return new DeleteCommand(taskNum - 1);
        } else {
            System.out.println(" OOPS!!! Please specify a task that you would like to add.");
            return new Command();
        }
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
        String deadlineStr = userInput.substring(dlIndex + 4);
        if (dlIndex == -1 || deadlineStr.isEmpty() || deadlineStr.isBlank()) {
            throw new IllegalValueException("OOPS!!! Please specify the date of your deadline.\n");
        }
        String dlDescription = userInput.substring(8, dlIndex - 1);
        if (dlDescription.isEmpty() || dlDescription.isBlank()) {
            throw new IllegalValueException("OOPS!!! Please include a description of your deadline.\n");
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
        if (fromIndex == -1 || toIndex == -1) {
            throw new IllegalValueException("OOPS!!! Please specify the start and end data of your event.\n");
        }
        String from = userInput.substring(fromIndex + 6, toIndex - 1);
        String to = userInput.substring(toIndex + 4);
        if (from.isEmpty() || from.isBlank() || to.isEmpty() || to.isBlank()) {
            throw new IllegalValueException("OOPS!!! Please specify the start and end data of your event.\n");
        }
        String eventDescription = userInput.substring(5, fromIndex - 1);
        if (eventDescription.isEmpty() || eventDescription.isBlank()) {
            throw new IllegalValueException("OOPS!!! Please include a description of your event.\n");
        }
        return new Event(eventDescription,
                userInput.substring(fromIndex + 6, toIndex - 1),
                userInput.substring(toIndex + 4));
    }
}
