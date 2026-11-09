import java.util.*;

class Employee {

    int id;
    String name;
    double salary;

    Employee(int id, String name, double salary) {
        this.id = id;
        this.name = name;
        this.salary = salary;
    }

    public String toString() {
        return id + " " + name + " " + salary;
    }
}

public class EmployeeSortingDemo {

    public static void main(String[] args) {

        List<Employee> employees = Arrays.asList(
                new Employee(101, "Ravi", 45000),
                new Employee(102, "Sai", 60000),
                new Employee(103, "Kiran", 35000),
                new Employee(104, "Arjun", 75000)
        );

        // Sort employees by salary in ascending order
        System.out.println("Ascending Salary:");

        employees.stream()
                .sorted(Comparator.comparingDouble(e -> e.salary))
                .forEach(System.out::println);

        // Sort employees by salary in descending order
        System.out.println("Descending Salary:");

        employees.stream()
                .sorted(Comparator.comparingDouble(
                        (Employee e) -> e.salary
                ).reversed())
                .forEach(System.out::println);

        // Sort employees alphabetically by name
        System.out.println("Sorting by Name:");

        employees.stream()
                .sorted(Comparator.comparing(e -> e.name))
                .forEach(System.out::println);
    }
}