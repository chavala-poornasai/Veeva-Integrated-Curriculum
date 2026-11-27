
public class FullTimeEmployee extends Employee {

    private double baseSalary;
    private double bonus;

    public FullTimeEmployee(int id, String name, String department,
                            double baseSalary, double bonus) {

        super(id, name, department);
        this.baseSalary = baseSalary;
        this.bonus = bonus;
    }

    @Override
    public double calculateSalary() {
        return baseSalary + bonus;
    }
}
