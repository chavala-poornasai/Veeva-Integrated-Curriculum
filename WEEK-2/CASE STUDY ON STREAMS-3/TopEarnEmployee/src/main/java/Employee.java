
import java.time.LocalDate;
import java.util.*;
import java.util.stream.*;

class Employee {
    int id;
    String name;
    String job;
    String qualification;
    LocalDate doj;
    double salary;
    String dept;

    Employee(int id, String name, String job,
             String qualification, LocalDate doj,
             double salary, String dept) {

        this.id = id;
        this.name = name;
        this.job = job;
        this.qualification = qualification;
        this.doj = doj;
        this.salary = salary;
        this.dept = dept;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public LocalDate getDoj() {
        return doj;
    }

    public void setDoj(LocalDate doj) {
        this.doj = doj;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    @Override
    public String toString() {
        return "ID: " + id +
                ", Name: " + name +
                ", Job: " + job +
                ", Qualification: " + qualification +
                ", DOJ: " + doj +
                ", Salary: " + salary +
                ", Department: " + dept;
    }
}