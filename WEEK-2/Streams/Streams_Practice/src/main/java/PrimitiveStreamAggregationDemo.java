import java.util.*;
import java.util.stream.*;

public class PrimitiveStreamAggregationDemo {

    public static void main(String[] args) {

        List<Integer> marks = Arrays.asList(
                75, 85, 90, 65, 80
        );

        // Calculate total marks
        int total = marks.stream()
                .mapToInt(Integer::intValue)
                .sum();

        System.out.println("Total Marks: " + total);

        // Calculate average marks
        OptionalDouble average = marks.stream()
                .mapToInt(Integer::intValue)
                .average();

        System.out.println(
                "Average Marks: " + average.orElse(0.0)
        );

        // Find maximum marks
        OptionalInt maximum = marks.stream()
                .mapToInt(Integer::intValue)
                .max();

        System.out.println(
                "Maximum Marks: " + maximum.orElse(0)
        );

        // Find minimum marks
        OptionalInt minimum = marks.stream()
                .mapToInt(Integer::intValue)
                .min();

        System.out.println(
                "Minimum Marks: " + minimum.orElse(0)
        );

        // Count elements
        long count = marks.stream()
                .mapToInt(Integer::intValue)
                .count();

        System.out.println("Number of Students: " + count);
    }
}