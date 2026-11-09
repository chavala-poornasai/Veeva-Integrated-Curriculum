import java.util.*;
import java.util.stream.*;

public class StreamCreationDemo {

    public static void main(String[] args) {

        // 1. Creating Stream from Collection
        List<String> names = Arrays.asList(
                "Ravi", "Sai", "Kiran", "Arjun"
        );

        names.stream().forEach(System.out::println);

        // 2. Creating Stream using Stream.of()
        Stream.of(10, 20, 30, 40)
                .forEach(System.out::println);

        // 3. Creating Stream from Array
        Integer[] numbers = {5, 10, 15, 20};

        Arrays.stream(numbers)
                .forEach(System.out::println);

        // 4. Creating an Empty Stream
        Stream.empty()
                .forEach(System.out::println);

        // 5. Creating Stream using generate()
        Stream.generate(() -> "Java")
                .limit(3)
                .forEach(System.out::println);

        // 6. Creating Stream using iterate()
        Stream.iterate(1, n -> n + 1)
                .limit(5)
                .forEach(System.out::println);
    }
}