import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Gumbo {
    public static void main(String[] args) {
        System.out.println("Hello! I'm Gumbo\n" +
                "What can I do for you?\n");

        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        ArrayList<String> strArr = new ArrayList<>();

        while (true) {
            String userInput = myObj.nextLine();  // Read user input
            if (userInput.equals("bye")) {
                System.out.println("Bye. Hope to see you again soon!");
                break;
            } else if (userInput.equals("list")) {
                int i = strArr.size();
                for (int j = 1; j < i+1; j++) {
                    System.out.println(j + ". " + strArr.get(j - 1));
                }
            } else {
                strArr.add(userInput);
                System.out.println("Added: " + userInput);
            }
        }
    }
}
