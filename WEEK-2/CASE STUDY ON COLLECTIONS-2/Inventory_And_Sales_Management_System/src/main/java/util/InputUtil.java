
package util;

import java.util.Scanner;
import java.math.BigDecimal;

public class InputUtil {

    private static final Scanner sc = new Scanner(System.in);



    public static int getInt(String message) {

        while (true) {
            System.out.print(message);

            try {
                return Integer.parseInt(sc.nextLine().trim());
            }
            catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a valid integer.");
            }
        }
    }



    public static String getString(String message) {
        System.out.print(message);
        return sc.nextLine();
    }


    public static BigDecimal getBigDecimal(String message) {
        while (true) {
            System.out.print(message);

            try {
                return new BigDecimal(sc.nextLine().trim());
            }
            catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a valid integer.");
            }
        }

    }
}
