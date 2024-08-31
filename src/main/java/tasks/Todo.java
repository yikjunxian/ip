public class Todo extends Task {
    public Todo(String description) {
        super(description);
    }

    @Override
    public String toTextString() {
        int status = super.isDone ? 1 : 0;
        return "T" + "," + status + "," + super.description;
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}

