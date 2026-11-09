import java.util.*;

public class MapTransformationDemo {

    public static void main(String[] args) {

        List<String> names = Arrays.asList(
                "ravi", "sai", "kiran", "arjun"
        );

        // Convert names into uppercase
        System.out.println("Uppercase Names:");

        names.stream()
                .map(name -> name.toUpperCase())
                .forEach(System.out::println);

        // Calculate the length of each name
        System.out.println("Name Lengths:");

        names.stream()
                .map(name -> name.length())
                .forEach(System.out::println);

        // Convert strings into their lengths
        List<Integer> lengths = names.stream()
                .map(String::length)
                .toList(); // Java 16+

        System.out.println(lengths);
    }
}