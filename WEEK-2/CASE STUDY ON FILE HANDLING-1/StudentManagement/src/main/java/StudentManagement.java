
import java.io.*;
import java.util.*;

public class StudentManagement {

    static final String FILE_NAME = "student.txt";

    static Scanner sc = new Scanner(System.in);

    // ================= INPUT METHODS =================

    static int readInt() {

        while (true) {
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("Enter a valid integer: ");
            }
        }
    }

    static int readPositiveInt() {

        while (true) {
            int value = readInt();

            if (value > 0) {
                return value;
            }

            System.out.print("Enter a positive number: ");
        }
    }

    static int readMark() {

        while (true) {
            int mark = readInt();

            if (mark >= 0 && mark <= 100) {
                return mark;
            }

            System.out.print("Enter marks between 0 and 100: ");
        }
    }

    static String readText(String field) {

        while (true) {

            String value = sc.nextLine().trim();

            if (!value.isEmpty() && !value.contains(",")) {
                return value;
            }

            System.out.print(
                    "Enter valid " + field + " without commas: "
            );
        }
    }

    // ================= MENU =================

    public static void main(String[] args) {

        int choice;

        do {

            System.out.println("\n===== STUDENT MANAGEMENT SYSTEM =====");
            System.out.println("1. Add Record");
            System.out.println("2. Read All Records");
            System.out.println("3. Read Record by Roll No");
            System.out.println("4. Read Record by Name");
            System.out.println("5. Delete Record by Roll No");
            System.out.println("6. Update Record by Roll No");
            System.out.println("7. Exit");

            System.out.print("Enter your choice: ");

            choice = readInt();

            switch (choice) {

                case 1:
                    addRecord();
                    break;

                case 2:
                    readAllRecords();
                    break;

                case 3:
                    readByRollNo();
                    break;

                case 4:
                    readByName();
                    break;

                case 5:
                    deleteByRollNo();
                    break;

                case 6:
                    updateRecord();
                    break;

                case 7:
                    System.out.println("Thank you! Application closed.");
                    break;

                default:
                    System.out.println("Invalid choice!");
            }

        } while (choice != 7);

        sc.close();
    }

    // ================= ADD RECORD =================

    static void addRecord() {

        try {

            ArrayList<Student> students = loadRecords();

            System.out.println("\n--- ADD STUDENT ---");

            System.out.print("Enter Roll No: ");
            int rollNo = readPositiveInt();

            for (Student s : students) {
                if (s.getRollNo() == rollNo) {
                    System.out.println("Roll number already exists!");
                    return;
                }
            }

            System.out.print("Enter Name: ");
            String name = readText("name");

            System.out.print("Enter Age: ");
            int age = readPositiveInt();

            System.out.print("Enter M1: ");
            int m1 = readMark();

            System.out.print("Enter M2: ");
            int m2 = readMark();

            System.out.print("Enter M3: ");
            int m3 = readMark();

            System.out.print("Enter M4: ");
            int m4 = readMark();

            System.out.print("Enter M5: ");
            int m5 = readMark();

            System.out.print("Enter Grade: ");
            String grade = readText("grade");

            Student s = new Student(
                    rollNo, name, age,
                    m1, m2, m3, m4, m5, grade
            );

            try (BufferedWriter writer =
                         new BufferedWriter(
                                 new FileWriter(FILE_NAME, true))) {

                writer.write(s.toFileRecord());
                writer.newLine();
            }

            System.out.println("Record added successfully!");

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // ================= READ ALL RECORDS =================

    static void readAllRecords() {

        System.out.println("\n--- ALL STUDENT RECORDS ---");

        try {

            ArrayList<Student> students = loadRecords();

            if (students.isEmpty()) {
                System.out.println("No records found!");
                return;
            }

            for (Student s : students) {
                s.display();
            }

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // ================= READ BY ROLL NUMBER =================

    static void readByRollNo() {

        System.out.print("\nEnter Roll No: ");
        int rollNo = readPositiveInt();

        try {

            ArrayList<Student> students = loadRecords();

            for (Student s : students) {

                if (s.getRollNo() == rollNo) {
                    System.out.println("Record found!");
                    s.display();
                    return;
                }
            }

            System.out.println("Student not found!");

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // ================= READ BY NAME =================

    static void readByName() {

        System.out.print("\nEnter Student Name: ");
        String name = readText("name");

        try {

            ArrayList<Student> students = loadRecords();

            boolean found = false;

            for (Student s : students) {

                if (s.getName().equalsIgnoreCase(name)) {
                    s.display();
                    found = true;
                }
            }

            if (!found) {
                System.out.println("Student not found!");
            }

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // ================= DELETE BY ROLL NUMBER =================

    static void deleteByRollNo() {

        System.out.print("\nEnter Roll No to delete: ");
        int rollNo = readPositiveInt();

        try {

            ArrayList<Student> students = loadRecords();

            boolean removed = students.removeIf(
                    s -> s.getRollNo() == rollNo
            );

            if (removed) {

                saveRecords(students);

                System.out.println("Record deleted successfully!");

            } else {

                System.out.println("Student not found!");
            }

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // ================= UPDATE BY ROLL NUMBER =================

    static void updateRecord() {

        System.out.print("\nEnter Roll No to update: ");
        int rollNo = readPositiveInt();

        try {

            ArrayList<Student> students = loadRecords();

            boolean found = false;

            for (int i = 0; i < students.size(); i++) {

                Student old = students.get(i);

                if (old.getRollNo() == rollNo) {

                    System.out.println("Existing Record:");
                    old.display();

                    System.out.print("Enter New Name: ");
                    String name = readText("name");

                    System.out.print("Enter New Age: ");
                    int age = readPositiveInt();

                    System.out.print("Enter New M1: ");
                    int m1 = readMark();

                    System.out.print("Enter New M2: ");
                    int m2 = readMark();

                    System.out.print("Enter New M3: ");
                    int m3 = readMark();

                    System.out.print("Enter New M4: ");
                    int m4 = readMark();

                    System.out.print("Enter New M5: ");
                    int m5 = readMark();

                    System.out.print("Enter New Grade: ");
                    String grade = readText("grade");

                    Student updated = new Student(
                            rollNo, name, age,
                            m1, m2, m3, m4, m5, grade
                    );

                    students.set(i, updated);

                    found = true;
                    break;
                }
            }

            if (found) {

                saveRecords(students);

                System.out.println("Record updated successfully!");

            } else {

                System.out.println("Student not found!");
            }

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // ================= LOAD RECORDS =================

    static ArrayList<Student> loadRecords() throws IOException {

        ArrayList<Student> students = new ArrayList<>();

        File file = new File(FILE_NAME);

        if (!file.exists()) {
            return students;
        }

        try (BufferedReader reader =
                     new BufferedReader(new FileReader(file))) {

            String line;
            int lineNo = 0;

            while ((line = reader.readLine()) != null) {

                lineNo++;

                if (line.trim().isEmpty()) {
                    continue;
                }

                try {

                    Student s = Student.fromFileRecord(line);

                    students.add(s);

                } catch (RuntimeException e) {

                    throw new IOException(
                            "Invalid record at line " + lineNo, e
                    );
                }
            }
        }

        return students;
    }

    // ================= SAVE RECORDS =================

    static void saveRecords(ArrayList<Student> students)
            throws IOException {

        try (BufferedWriter writer =
                     new BufferedWriter(new FileWriter(FILE_NAME))) {

            for (Student s : students) {

                writer.write(s.toFileRecord());
                writer.newLine();
            }
        }
    }
}
