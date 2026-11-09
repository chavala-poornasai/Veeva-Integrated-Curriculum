import java.util.*;

public class DistinctLimitSkipDemo {

    public static void main(String[] args) {

        List<Integer> numbers = Arrays.asList(
                10, 20, 10, 30, 40, 20, 50, 60
        );

        // Remove duplicate elements
        System.out.println("Distinct Elements:");

        numbers.stream()
                .distinct()
                .forEach(System.out::println);

        // Retrieve first three elements
        System.out.println("First Three Elements:");

        numbers.stream()
                .limit(3)
                .forEach(System.out::println);

        // Skip first three elements
        System.out.println("After Skipping Three Elements:");

        numbers.stream()
                .skip(3)
                .forEach(System.out::println);
    }
}