import java.util.*;
import java.util.stream.*;

public class FlatMapTransformationDemo {

    public static void main(String[] args) {

        List<List<Integer>> numbers = Arrays.asList(
                Arrays.asList(10, 20, 30),
                Arrays.asList(40, 50),
                Arrays.asList(60, 70, 80)
        );

        // Flatten nested lists
        System.out.println("Flattened Elements:");

        numbers.stream()
                .flatMap(list -> list.stream())
                .forEach(System.out::println);

        // Extract all student names from multiple groups
        List<List<String>> studentGroups = Arrays.asList(
                Arrays.asList("Ravi", "Sai"),
                Arrays.asList("Kiran", "Arjun"),
                Arrays.asList("Rahul", "Vamsi")
        );

        System.out.println("All Student Names:");

        studentGroups.stream()
                .flatMap(group -> group.stream())
                .forEach(System.out::println);
    }
}