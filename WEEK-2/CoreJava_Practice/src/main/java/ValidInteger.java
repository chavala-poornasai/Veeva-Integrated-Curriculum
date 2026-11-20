import java.util.Scanner;

public class ValidInteger {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int validInteger = 0;

        while (true) {
            System.out.print("Please enter an integer: ");

            // Check if the next input token is an integer
            if (scanner.hasNextInt()) {
                validInteger = scanner.nextInt();
                break; // Exit the loop since we got a valid integer
            } else {
                System.out.println("Invalid input! That is not an integer. Try again.\n");
                scanner.next(); // Clear the invalid input from the buffer
            }
        }

        System.out.println("Success! You entered the integer: " + validInteger);
        scanner.close();
    }


}
