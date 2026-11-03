
public class Student {

    private int rollNo;
    private String name;
    private int age;

    private int m1, m2, m3, m4, m5;

    private String grade;

    public Student(int rollNo, String name, int age,
                   int m1, int m2, int m3, int m4, int m5,
                   String grade) {

        this.rollNo = rollNo;
        this.name = name;
        this.age = age;
        this.m1 = m1;
        this.m2 = m2;
        this.m3 = m3;
        this.m4 = m4;
        this.m5 = m5;
        this.grade = grade;
    }

    public int getRollNo() {
        return rollNo;
    }

    public String getName() {
        return name;
    }

    public void display() {

        System.out.println("\n-----------------------------");
        System.out.println("Roll No : " + rollNo);
        System.out.println("Name    : " + name);
        System.out.println("Age     : " + age);
        System.out.println("M1      : " + m1);
        System.out.println("M2      : " + m2);
        System.out.println("M3      : " + m3);
        System.out.println("M4      : " + m4);
        System.out.println("M5      : " + m5);
        System.out.println("Grade   : " + grade);
        System.out.println("-----------------------------");
    }

    public String toFileRecord() {

        return rollNo + "," + name + "," + age + ","
                + m1 + "," + m2 + "," + m3 + ","
                + m4 + "," + m5 + "," + grade;
    }

    public static Student fromFileRecord(String line) {

        String[] data = line.split(",", -1);

        if (data.length != 9) {
            throw new IllegalArgumentException("Invalid record format");
        }

        return new Student(
                Integer.parseInt(data[0]),
                data[1],
                Integer.parseInt(data[2]),
                Integer.parseInt(data[3]),
                Integer.parseInt(data[4]),
                Integer.parseInt(data[5]),
                Integer.parseInt(data[6]),
                Integer.parseInt(data[7]),
                data[8]
        );
    }
}

