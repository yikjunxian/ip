package Gumbo.tasks;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Event extends Task {

    protected LocalDate to;
    protected LocalDate from;

    DateTimeFormatter inputDateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    DateTimeFormatter outputDateFormat = DateTimeFormatter.ofPattern("MMM dd yyyy");

    public Event(String description, String from, String to) {
        super(description);
        try {
            this.to = LocalDate.parse(to, inputDateFormat);
            this.from = LocalDate.parse(from, inputDateFormat);
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Please input date format as [yyyy-MM-dd].");
        }
    }

    @Override
    public String toTextString() {
        int status = super.isDone ? 1 : 0;
        return "E" + "," + status + "," + super.description + "," + this.from + "," + this.to;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from.format(outputDateFormat)
                + " to: " + to.format(outputDateFormat) + ")";
    }
}

