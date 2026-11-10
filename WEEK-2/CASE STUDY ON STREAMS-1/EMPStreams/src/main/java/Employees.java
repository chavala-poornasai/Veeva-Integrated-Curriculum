public class Employees {
    private int Empid;
    private String EmpName;
    private String EmpDept;
    private int age;

    public Employees( int empid, String empName,String empDept, int age) {
        EmpName = empName;
        Empid = empid;
        EmpDept = empDept;
        this.age = age;
    }



    public String getEmpDept() {
        return EmpDept;
    }

    public void setEmpDept(String empDept) {
        EmpDept = empDept;
    }

    public String getEmpName() {
        return EmpName;
    }

    public void setEmpName(String empName) {
        EmpName = empName;
    }

    public int getEmpid() {
        return Empid;
    }

    public void setEmpid(int empid) {
        Empid = empid;
    }
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Employees{" +
                "Empid=" + Empid +
                ", EmpName='" + EmpName + '\'' +
                ", EmpDept='" + EmpDept + '\'' +
                '}';
    }
}
