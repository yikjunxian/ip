import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Gumbo {

    public static class Task {
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
            return this.description;
        }

    }
    public static void main(String[] args) {
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
                for (int j = 1; j < i+1; j++) {
                    Task x = taskArr.get(j - 1);
                    System.out.println(j + ". " + "[" + x.getStatusIcon() + "] " + x);
                }
            } else if (userInput.startsWith("mark")) {
                int taskNum =  Character.getNumericValue(userInput.charAt(5));
                Task completedTask = taskArr.get(taskNum-1);
                completedTask.markAsDone();
                System.out.println("Nice! I've marked this task as done:\n"
                                    + " [X] " + completedTask);
            } else if (userInput.startsWith("unmark")) {
                int taskNum =  Character.getNumericValue(userInput.charAt(7));
                Task completedTask = taskArr.get(taskNum-1);
                completedTask.markAsUndone();
                System.out.println("OK, I've marked this task as not done yet:\n"
                        + " [ ] " + completedTask);
            } else {
                taskArr.add(new Task(userInput));
                System.out.println("Added: " + userInput);
            }
        }
    }
}
