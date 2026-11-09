import java.util.*;

class TestResult {

    int testId;
    String testName;
    String status;
    long executionTime;

    TestResult(int testId, String testName,
               String status, long executionTime) {

        this.testId = testId;
        this.testName = testName;
        this.status = status;
        this.executionTime = executionTime;
    }

    public String toString() {
        return testId + " | " + testName + " | "
                + status + " | " + executionTime + " ms";
    }
}

public class TestResultAnalysisDemo {

    public static void main(String[] args) {

        List<TestResult> results = Arrays.asList(

                new TestResult(101, "LoginTest", "PASS", 120),
                new TestResult(102, "RegisterTest", "FAIL", 250),
                new TestResult(103, "SearchTest", "PASS", 180),
                new TestResult(104, "PaymentTest", "FAIL", 300),
                new TestResult(105, "LogoutTest", "PASS", 100),
                new TestResult(106, "ProfileTest", "SKIPPED", 0)
        );

        // Display all test results
        System.out.println("All Test Results:");

        results.stream()
                .forEach(System.out::println);

        // Extract failed test cases
        System.out.println("\nFailed Test Cases:");

        results.stream()
                .filter(test -> test.status.equals("FAIL"))
                .forEach(System.out::println);

        // Count passed test cases
        long passed = results.stream()
                .filter(test -> test.status.equals("PASS"))
                .count();

        System.out.println("\nPassed Tests: " + passed);

        // Count failed test cases
        long failed = results.stream()
                .filter(test -> test.status.equals("FAIL"))
                .count();

        System.out.println("Failed Tests: " + failed);

        // Find the slowest test case
        Optional<TestResult> slowest = results.stream()
                .filter(test -> !test.status.equals("SKIPPED"))
                .max(Comparator.comparingLong(
                        test -> test.executionTime
                ));

        System.out.println("\nSlowest Test:");

        slowest.ifPresent(System.out::println);

        // Check whether any test case failed
        boolean hasFailures = results.stream()
                .anyMatch(test ->
                        test.status.equals("FAIL")
                );

        System.out.println("\nHas Failures: " + hasFailures);
    }
}