import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Gumbo {

    public static void main(String[] args) {

        class Task {
            protected String description;
            protected boolean isDone;

            public Task(String description) {
                this.description = description;
                this.isDone = false;
            }

            public String getStatusIcon() {
                return (isDone ? "X" : " "); // mark done task with X
            }

            public void markAsDone() {
                this.isDone = true;
            }

            public void markAsUndone() {
                this.isDone = false;
            }

            @Override
            public String toString() {
                return "[" + this.getStatusIcon() + "] " + this.description;
            }

        }
        class Deadline extends Task {

            protected String by;

            public Deadline(String description, String by) {
                super(description);
                this.by = by;
            }

            @Override
            public String toString() {
                return "[D]" + super.toString() + " (by: " + by + ")";
            }
        }
        class Todo extends Task {

            public Todo(String description) {
                super(description);
            }

            @Override
            public String toString() {
                return "[T]" + super.toString();
            }
        }
        class Event extends Task {

            protected String to;
            protected String from;

            public Event(String description, String from, String to) {
                super(description);
                this.to = to;
                this.from = from;
            }

            @Override
            public String toString() {
                return "[E]" + super.toString() + " (from: " + from + " to: " + to + ")";
            }
        }

        class GumboException extends Exception {
            GumboException(String msg) {
                super(msg);
            }
        }


        System.out.println("Hello! I'm Gumbo\n" +
                "What can I do for you?\n");

        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        ArrayList<Task> taskArr = new ArrayList<>();


        while (true) {
            String userInput = myObj.nextLine();  // Read user input
            if (userInput.equals("bye")) {
                System.out.println("Bye. Hope to see you again soon!");
                break;
            } else if (userInput.equals("list")) {
                int i = taskArr.size();
                System.out.println("Here are the tasks in your list:\n");
                for (int j = 1; j < i + 1; j++) {
                    Task x = taskArr.get(j - 1);
                    System.out.println(j + ". " + x);
                }
            } else if (userInput.startsWith("mark")) {
                int taskNum = Character.getNumericValue(userInput.charAt(5));
                Task completedTask = taskArr.get(taskNum - 1);
                completedTask.markAsDone();
                System.out.println("Nice! I've marked this task as done:\n"
                        + completedTask);
            } else if (userInput.startsWith("unmark")) {
                int taskNum = Character.getNumericValue(userInput.charAt(7));
                Task completedTask = taskArr.get(taskNum - 1);
                completedTask.markAsUndone();
                System.out.println("OK, I've marked this task as not done yet:\n"
                        + completedTask);
            } else if (userInput.startsWith("deadline")) {
                try {
                    int dlIndex = userInput.lastIndexOf("/by");
                    String by = userInput.substring(dlIndex + 4);
                    if (dlIndex == -1 || by.isEmpty() || by.isBlank()) {
                        throw new GumboException("OOPS!!! Please specify the date of your deadline.\n");
                    }
                    String dlDescription = userInput.substring(8, dlIndex - 1);
                    if (dlDescription.isEmpty() || dlDescription.isBlank()) {
                        throw new GumboException("OOPS!!! Please include a description of your deadline.\n");
                    }

                    Deadline newDeadline = new Deadline(dlDescription, by);
                    taskArr.add(newDeadline);
                    System.out.println("Got it. I've added this task:\n"
                            + newDeadline + "\n"
                            + "Now you have " + taskArr.size() + " tasks in the list");
                } catch (GumboException e) {
                    System.out.println(e.getMessage());
                }
            } else if (userInput.startsWith("todo")) {
                try {
                    String todoDescription = userInput.substring(4);
                    if (todoDescription.isEmpty() || todoDescription.isBlank()) {
                        throw new GumboException("OOPS!!! Please include a description of your todo.\n");
                    }
                    Todo newTodo = new Todo(todoDescription);
                    taskArr.add(newTodo);
                    System.out.println("Got it. I've added this task:\n"
                            + newTodo + "\n"
                            + "Now you have " + taskArr.size() + " tasks in the list");
                } catch (GumboException e) {
                    System.out.println(e.getMessage());
                }
            } else if (userInput.startsWith("event")) {
                try {
                    int fromIndex = userInput.lastIndexOf("/from");
                    int toIndex = userInput.lastIndexOf("/to");
                    if (fromIndex == -1 || toIndex == -1) {
                        throw new GumboException("OOPS!!! Please specify the start and end data of your event.\n");
                    }
                    String from = userInput.substring(fromIndex + 6, toIndex - 1);
                    String to = userInput.substring(toIndex + 4);
                    if (from.isEmpty() || from.isBlank() || to.isEmpty() || to.isBlank()) {
                        throw new GumboException("OOPS!!! Please specify the start and end data of your event.\n");
                    }
                    String eventDescription = userInput.substring(5, fromIndex - 1);
                    if (eventDescription.isEmpty() || eventDescription.isBlank()) {
                        throw new GumboException("OOPS!!! Please include a description of your event.\n");
                    }
                    Event newEvent = new Event(eventDescription,
                            userInput.substring(fromIndex + 6, toIndex - 1),
                            userInput.substring(toIndex + 4));
                    taskArr.add(newEvent);
                    System.out.println("Got it. I've added this task:\n"
                            + newEvent + "\n"
                            + "Now you have " + taskArr.size() + " tasks in the list");
                } catch (GumboException e) {
                    System.out.println(e.getMessage());
                }
            } else if (userInput.startsWith("delete")) {
                int taskNum = Character.getNumericValue(userInput.charAt(7));
                Task deletedTask = taskArr.get(taskNum - 1);
                taskArr.remove(taskNum - 1);
                System.out.println("Noted. I've removed this task:\n"
                        + deletedTask + "\n"
                        + "Now you have " + taskArr.size() + " tasks in the list");
            } else {
                System.out.println(" OOPS!!! Please specify a task that you would like to add.");
            }
        }
    }
}
