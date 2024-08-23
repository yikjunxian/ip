import java.util.Scanner;

public class Gumbo {
    public static void main(String[] args) {
        System.out.println("Hello! I'm Gumbo\n" +
                "What can I do for you?\n");

        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        while (true) {
            String userInput = myObj.nextLine();  // Read user input
            if (userInput.equals("bye")) {
                System.out.println("Bye. Hope to see you again soon!");
                break;
            } else {
                System.out.println(userInput);
            }
        }
    }
}
