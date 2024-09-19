package gumbo.tasks;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import gumbo.exceptions.IllegalValueException;

/**
 * Represents a task with a deadline.
 */
public class Deadline extends Task {
    protected LocalDate deadline;
    private DateTimeFormatter inputDateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private DateTimeFormatter outputDateFormat = DateTimeFormatter.ofPattern("MMM dd yyyy");

    /**
     * Constructs a Deadline task with the specified description and deadline.
     *
     * @param description the description of the task
     * @param deadline    the deadline of the task in the format "yyyy-MM-dd"
     * @throws IllegalValueException if the deadline is not in the correct format
     */
    public Deadline(String description, String deadline) throws IllegalValueException {
        super(description);
        try {
            this.deadline = LocalDate.parse(deadline, inputDateFormat);
        } catch (DateTimeParseException e) {
            throw new IllegalValueException("Invalid date format. Please input date format as [yyyy-MM-dd].");
        }
    }

    /**
     * Updates the deadline of the task.
     *
     * @param newDeadline the new deadline in the format "yyyy-MM-dd"
     */
    public void updateDeadline(String newDeadline) {
        try {
            this.deadline = LocalDate.parse(newDeadline, inputDateFormat);
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Please input date format as [yyyy-MM-dd].");
        }
    }

    /**
     * Returns a string representation of the task suitable for saving to a file.
     *
     * @return a string representation of the task
     */
    @Override
    public String toTextString() {
        int status = super.isDone ? 1 : 0;
        return "D" + "," + status + "," + super.description + ","
                + deadline;
    }

    /**
     * Returns a string representation of the task for display.
     *
     * @return a string representation of the task including its deadline
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + deadline.format(outputDateFormat) + ")";
    }
}
