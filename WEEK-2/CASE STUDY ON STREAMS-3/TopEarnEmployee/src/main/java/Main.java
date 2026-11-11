import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {

        List<Employee> employees = Arrays.asList(

                new Employee(1, "Sree", "Developer",
                        "B.Tech", LocalDate.of(2022, 5, 10),
                        60000, "IT"),

                new Employee(2, "Ram", "Tester",
                        "M.Tech", LocalDate.of(2021, 3, 15),
                        75000, "IT"),

                new Employee(3, "John", "Manager",
                        "MBA", LocalDate.of(2020, 6, 20),
                        90000, "HR"),

                new Employee(4, "David", "HR Executive",
                        "BBA", LocalDate.of(2023, 1, 10),
                        50000, "HR"),

                new Employee(5, "Kiran", "Analyst",
                        "B.Tech", LocalDate.of(2021, 8, 12),
                        85000, "Finance"),

                new Employee(6, "Arun", "Accountant",
                        "M.Com", LocalDate.of(2022, 4, 18),
                        65000, "Finance"),

                new Employee(7, "Ravi", "Developer",
                        "B.Tech", LocalDate.of(2023, 2, 14),
                        80000, "IT")
        );

        Map<String, Optional<Employee>> result =
                employees.stream()
                        .collect(Collectors.groupingBy(
                                e -> e.dept,
                                Collectors.maxBy(
                                        Comparator.comparingDouble(e -> e.salary)
                                )
                        ));

        result.forEach((dept, employee) -> {
            System.out.println("Department: " + dept);
            System.out.println(employee.get());
            System.out.println("--------------------------------");
        });
    }
}
