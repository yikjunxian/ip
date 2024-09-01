package gumbo.tasks;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Deadline extends Task {
    protected LocalDate deadline;
    private DateTimeFormatter inputDateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private DateTimeFormatter outputDateFormat = DateTimeFormatter.ofPattern("MMM dd yyyy");
    public Deadline(String description, String deadline) {
        super(description);
        try {
            this.deadline = LocalDate.parse(deadline, inputDateFormat);
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Please input date format as [yyyy-MM-dd].");
        }
    }

    @Override
    public String toTextString() {
        int status = super.isDone ? 1 : 0;
        return "D" + "," + status + "," + super.description + ","
                + deadline.format(outputDateFormat);
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + deadline.format(outputDateFormat) + ")";
    }
}
