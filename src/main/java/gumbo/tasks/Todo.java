package gumbo.tasks;

/**
 * Represents a task that needs to be done.
 */
public class Todo extends Task {
    /**
     * Constructs a Todo task with the specified description.
     *
     * @param description the description of the task
     */
    public Todo(String description) {
        super(description);
    }

    /**
     * Returns a string representation of the Todo task suitable for saving to a file.
     *
     * @return a CSV-formatted string representation of the Todo task
     */
    @Override
    public String toTextString() {
        int status = super.isDone ? 1 : 0;
        return "T" + "," + status + "," + super.description;
    }

    /**
     * Returns a string representation of the Todo task for display.
     *
     * @return a string representation of the Todo task
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}

