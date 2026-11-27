
import java.util.Scanner;
public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        PayrollManager manager = new PayrollManager();

        System.out.print("Enter number of employees: ");
        int n = sc.nextInt();

        for (int i = 1; i <= n; i++) {

            System.out.println("\nEmployee " + i);

            System.out.print("Enter Employee ID: ");
            int id = sc.nextInt();

            sc.nextLine();

            System.out.print("Enter Employee Name: ");
            String name = sc.nextLine();

            System.out.print("Enter Department: ");
            String department = sc.nextLine();

            System.out.println("1. Full Time Employee");
            System.out.println("2. Contract Employee");

            System.out.print("Enter Employee Type: ");
            int choice = sc.nextInt();

            if (choice == 1) {

                System.out.print("Enter Base Salary: ");
                double baseSalary = sc.nextDouble();

                System.out.print("Enter Bonus: ");
                double bonus = sc.nextDouble();

                manager.addEmployee(
                        new FullTimeEmployee(id, name, department,
                                baseSalary, bonus));

            } else if (choice == 2) {

                System.out.print("Enter Hourly Rate: ");
                double hourlyRate = sc.nextDouble();

                System.out.print("Enter Hours Worked: ");
                int hoursWorked = sc.nextInt();

                manager.addEmployee(
                        new ContractEmployee(id, name, department,
                                hourlyRate, hoursWorked));

            } else{
                System.out.println("Invalid Employee Type!");
                i--;
            }
        }

        System.out.println("\n--- Payroll Details ---");

        System.out.println("Department-wise Total Salary: "
                + manager.getDepartmentWiseTotalSalary());

        System.out.print("\nEnter Salary Threshold: ");
        double threshold = sc.nextDouble();

        System.out.println("Top Earners: "
                + manager.getTopEarners(threshold));

        sc.close();
    }
}
