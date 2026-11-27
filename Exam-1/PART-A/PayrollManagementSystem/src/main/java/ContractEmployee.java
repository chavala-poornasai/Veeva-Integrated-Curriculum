
public class ContractEmployee extends Employee {

    private double hourlyRate;
    private int hoursWorked;

    public ContractEmployee(int id, String name, String department,
                            double hourlyRate, int hoursWorked) {

        super(id, name, department);
        this.hourlyRate = hourlyRate;
        this.hoursWorked = hoursWorked;
    }

    @Override
    public double calculateSalary() {
        return hourlyRate * hoursWorked;
    }
}
