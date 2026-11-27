
import java.util.*;
import java.util.stream.Collectors;

public class PayrollManager {

    private List<Employee> empList = new ArrayList<>();

    public void addEmployee(Employee e) {
        empList.add(e);
    }

    // Calculate department-wise salary
    public Map<String, Double> getDepartmentWiseTotalSalary() {

        Map<String, Double> deptWiseTotalSalary = empList.stream()
                                                .collect(Collectors.groupingBy(
                                                e -> e.getDepartment(),
                                        Collectors.summingDouble(e -> e.calculateSalary())));

        return deptWiseTotalSalary;
    }

    // Get employees earning above threshold
    public List<String> getTopEarners(double threshold) {

        List<String> topEarners = empList.stream()
                .filter(e -> e.calculateSalary() > threshold)
                .map(e -> e.getName())
                .sorted()
                .collect(Collectors.toList());

        return topEarners;
    }
}
