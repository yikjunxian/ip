public class Event extends Task {

    protected String to;
    protected String from;

    public Event(String description, String from, String to) {
        super(description);
        this.to = to;
        this.from = from;
    }

    @Override
    public String toTextString() {
        int status = super.isDone ? 1 : 0;
        return "E" + "," + status + "," + super.description + "," + this.from + "," + this.to;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from + " to: " + to + ")";
    }
}

