import java.util.*;

public class SearchingMatchingDemo {

    public static void main(String[] args) {

        List<Integer> numbers = Arrays.asList(
                10, 20, 30, 40, 50
        );

        // Find the first element greater than 25
        Optional<Integer> first = numbers.stream()
                .filter(n -> n > 25)
                .findFirst();

        System.out.println("First Matching Element: "
                + first.orElse(-1));

        // Find any element greater than 30
        Optional<Integer> any = numbers.stream()
                .filter(n -> n > 30)
                .findAny();

        System.out.println("Any Matching Element: "
                + any.orElse(-1));

        // Check whether any number is even
        System.out.println(
                "Any Even Number: " +
                        numbers.stream().anyMatch(n -> n % 2 == 0)
        );

        // Check whether all numbers are positive
        System.out.println(
                "All Numbers Positive: " +
                        numbers.stream().allMatch(n -> n > 0)
        );

        // Check whether no number is negative
        System.out.println(
                "No Negative Numbers: " +
                        numbers.stream().noneMatch(n -> n < 0)
        );
    }
}