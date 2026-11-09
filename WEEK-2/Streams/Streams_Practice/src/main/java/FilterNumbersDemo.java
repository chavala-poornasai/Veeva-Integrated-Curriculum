import java.util.*;
import java.util.stream.*;

public class FilterNumbersDemo {

    public static void main(String[] args) {

        List<Integer> numbers = Arrays.asList(
                10, 15, 20, 25, 30, 35, 40, 45
        );

        // Extract even numbers
        System.out.println("Even Numbers:");

        numbers.stream()
                .filter(n -> n % 2 == 0)
                .forEach(System.out::println);

        // Extract numbers greater than 25
        System.out.println("Numbers Greater Than 25:");

        numbers.stream()
                .filter(n -> n > 25)
                .forEach(System.out::println);
    }
}