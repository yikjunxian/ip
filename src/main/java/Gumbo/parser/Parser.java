package parser;

import commands.*;
import exceptions.IllegalValueException;
import tasks.Deadline;
import tasks.Event;
import tasks.Task;
import tasks.Todo;

public class Parser {

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
