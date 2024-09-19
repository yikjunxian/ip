package gumbo.tasks;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import gumbo.exceptions.IllegalValueException;

/**
 * Represents a task with a time range for an event.
 */
public class Event extends Task {

    protected LocalDate to;
    protected LocalDate from;

    private DateTimeFormatter inputDateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private DateTimeFormatter outputDateFormat = DateTimeFormatter.ofPattern("MMM dd yyyy");
    /**
     * Constructs an Event task with the specified description, start date, and end date.
     *
     * @param description the description of the event
     * @param from        the start date of the event in the format "yyyy-MM-dd"
     * @param to          the end date of the event in the format "yyyy-MM-dd"
     * @throws IllegalValueException if either date is not in the correct format
     */
    public Event(String description, String from, String to) throws IllegalValueException {
        super(description);
        try {
            this.to = LocalDate.parse(to, inputDateFormat);
            this.from = LocalDate.parse(from, inputDateFormat);
        } catch (DateTimeParseException e) {
            throw new IllegalValueException("Invalid date format. Please input date format as [yyyy-MM-dd].");
        }
    }
    /**
     * Updates the start date of the event.
     *
     * @param from the new start date in the format "yyyy-MM-dd"
     */
    public void updateFrom(String from) {
        try {
            this.from = LocalDate.parse(from, inputDateFormat);
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Please input date format as [yyyy-MM-dd].");
        }
    }
    /**
     * Updates the end date of the event.
     *
     * @param to the new end date in the format "yyyy-MM-dd"
     */
    public void updateTo(String to) {
        try {
            this.to = LocalDate.parse(to, inputDateFormat);
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Please input date format as [yyyy-MM-dd].");
        }
    }
    /**
     * Returns a string representation of the event suitable for saving to a file.
     *
     * @return a string representation of the event
     */
    @Override
    public String toTextString() {
        int status = super.isDone ? 1 : 0;
        return "E" + "," + status + "," + super.description + "," + this.from + "," + this.to;
    }
    /**
     * Returns a string representation of the event for display.
     *
     * @return a string representation of the event including its time range
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from.format(outputDateFormat)
                + " to: " + to.format(outputDateFormat) + ")";
    }
}

