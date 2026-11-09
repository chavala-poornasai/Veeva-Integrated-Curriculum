import java.util.*;

public class ReduceOperationDemo {

    public static void main(String[] args) {

        List<Integer> numbers = Arrays.asList(
                1, 2, 3, 4, 5
        );

        // Calculate sum using reduce()
        int sum = numbers.stream()
                .reduce(0, (a, b) -> a + b);

        System.out.println("Sum: " + sum);

        // Calculate product using reduce()
        int product = numbers.stream()
                .reduce(1, (a, b) -> a * b);

        System.out.println("Product: " + product);

        // Find maximum number using reduce()
        int maximum = numbers.stream()
                .reduce(Integer.MIN_VALUE,
                        (a, b) -> a > b ? a : b);

        System.out.println("Maximum: " + maximum);

        // Find sum without identity
        Optional<Integer> result = numbers.stream()
                .reduce((a, b) -> a + b);

        System.out.println("Optional Sum: " + result.orElse(0));
    }
}