
import java.io.*;
import java.util.*;

public class StudentManagement_1     {

    static final String FILE_NAME = "student.txt";

    static Scanner sc = new Scanner(System.in);

    // ========== INPUT METHODS ==========

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

    // ========== VALIDATE RECORD ==========

    static String[] splitRecord(String line) {

        String[] data = line.split(",", -1);

        if (data.length != 9) {
            throw new IllegalArgumentException("Invalid record");
        }

        for (String value : data) {
            if (value.trim().isEmpty()) {
                throw new IllegalArgumentException("Empty field");
            }
        }

        int rollNo = Integer.parseInt(data[0]);
        int age = Integer.parseInt(data[2]);

        if (rollNo <= 0 || age <= 0) {
            throw new IllegalArgumentException("Invalid roll number or age");
        }

        for (int i = 3; i <= 7; i++) {
            int mark = Integer.parseInt(data[i]);

            if (mark < 0 || mark > 100) {
                throw new IllegalArgumentException("Invalid marks");
            }
        }

        return data;
    }

    // ========== MAIN MENU ==========

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
                    System.out.println("Application closed.");
                    break;

                default:
                    System.out.println("Invalid choice!");
            }

        } while (choice != 7);

        sc.close();
    }

    // ========== 1. ADD RECORD ==========

    static void addRecord() {

        try {

            ArrayList<String> records = loadRecords();

            System.out.println("\n--- ADD RECORD ---");

            System.out.print("Enter Roll No: ");
            int rollNo = readPositiveInt();

            // Check duplicate roll number

            for (String record : records) {

                String[] data = splitRecord(record);

                if (Integer.parseInt(data[0]) == rollNo) {

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

            // Convert student details into one String

            String record = rollNo + "," + name + "," + age + ","
                    + m1 + "," + m2 + "," + m3 + ","
                    + m4 + "," + m5 + "," + grade;

            // Append record to file

            try (BufferedWriter writer =
                         new BufferedWriter(new FileWriter(FILE_NAME, true))) {

                writer.write(record);
                writer.newLine();
            }

            System.out.println("Record added successfully!");

        } catch (IOException | IllegalArgumentException e) {

            System.out.println("Error: " + e.getMessage());
        }
    }

    // ========== 2. READ ALL RECORDS ==========

    static void readAllRecords() {

        System.out.println("\n--- ALL STUDENT RECORDS ---");

        try {

            ArrayList<String> records = loadRecords();

            if (records.isEmpty()) {

                System.out.println("No records found!");
                return;
            }

            for (String record : records) {

                displayRecord(record);
            }

        } catch (IOException | IllegalArgumentException e) {

            System.out.println("Error: " + e.getMessage());
        }
    }

    // ========== DISPLAY RECORD ==========

    static void displayRecord(String record) {

        String[] data = splitRecord(record);

        System.out.println("\n-----------------------------");

        System.out.println("Roll No : " + data[0]);
        System.out.println("Name    : " + data[1]);
        System.out.println("Age     : " + data[2]);

        System.out.println("M1      : " + data[3]);
        System.out.println("M2      : " + data[4]);
        System.out.println("M3      : " + data[5]);
        System.out.println("M4      : " + data[6]);
        System.out.println("M5      : " + data[7]);

        System.out.println("Grade   : " + data[8]);

        System.out.println("-----------------------------");
    }

    // ========== 3. SEARCH BY ROLL NUMBER ==========

    static void readByRollNo() {

        System.out.print("\nEnter Roll No: ");
        int rollNo = readPositiveInt();

        try {

            ArrayList<String> records = loadRecords();

            for (String record : records) {

                String[] data = splitRecord(record);

                if (Integer.parseInt(data[0]) == rollNo) {

                    System.out.println("Record found!");

                    displayRecord(record);
                    return;
                }
            }

            System.out.println("Student not found!");

        } catch (IOException | IllegalArgumentException e) {

            System.out.println("Error: " + e.getMessage());
        }
    }

    // ========== 4. SEARCH BY NAME ==========

    static void readByName() {

        System.out.print("\nEnter Student Name: ");
        String name = readText("name");

        try {

            ArrayList<String> records = loadRecords();

            boolean found = false;

            for (String record : records) {

                String[] data = splitRecord(record);

                if (data[1].equalsIgnoreCase(name)) {

                    displayRecord(record);
                    found = true;
                }
            }

            if (!found) {
                System.out.println("Student not found!");
            }

        } catch (IOException | IllegalArgumentException e) {

            System.out.println("Error: " + e.getMessage());
        }
    }

    // ========== 5. DELETE BY ROLL NUMBER ==========

    static void deleteByRollNo() {

        System.out.print("\nEnter Roll No to delete: ");
        int rollNo = readPositiveInt();

        try {

            ArrayList<String> records = loadRecords();

            boolean removed = records.removeIf(record -> {

                String[] data = splitRecord(record);

                return Integer.parseInt(data[0]) == rollNo;
            });

            if (removed) {

                saveRecords(records);

                System.out.println("Record deleted successfully!");

            } else {

                System.out.println("Student not found!");
            }

        } catch (IOException | IllegalArgumentException e) {

            System.out.println("Error: " + e.getMessage());
        }
    }

    // ========== 6. UPDATE BY ROLL NUMBER ==========

    static void updateRecord() {

        System.out.print("\nEnter Roll No to update: ");
        int rollNo = readPositiveInt();

        try {

            ArrayList<String> records = loadRecords();

            boolean found = false;

            for (int i = 0; i < records.size(); i++) {

                String[] data = splitRecord(records.get(i));

                if (Integer.parseInt(data[0]) == rollNo) {

                    System.out.println("Existing Record:");

                    displayRecord(records.get(i));

                    System.out.println("\nEnter New Details:");

                    System.out.print("Enter New Name: ");
                    data[1] = readText("name");

                    System.out.print("Enter New Age: ");
                    data[2] = String.valueOf(readPositiveInt());

                    System.out.print("Enter New M1: ");
                    data[3] = String.valueOf(readMark());

                    System.out.print("Enter New M2: ");
                    data[4] = String.valueOf(readMark());

                    System.out.print("Enter New M3: ");
                    data[5] = String.valueOf(readMark());

                    System.out.print("Enter New M4: ");
                    data[6] = String.valueOf(readMark());

                    System.out.print("Enter New M5: ");
                    data[7] = String.valueOf(readMark());

                    System.out.print("Enter New Grade: ");
                    data[8] = readText("grade");

                    // Convert updated array back into String

                    String updatedRecord = String.join(",", data);

                    records.set(i, updatedRecord);

                    found = true;
                    break;
                }
            }

            if (found) {

                saveRecords(records);

                System.out.println("Record updated successfully!");

            } else {

                System.out.println("Student not found!");
            }

        } catch (IOException | IllegalArgumentException e) {

            System.out.println("Error: " + e.getMessage());
        }
    }

    // ========== LOAD ALL RECORDS ==========

    static ArrayList<String> loadRecords() throws IOException {

        ArrayList<String> records = new ArrayList<>();

        File file = new File(FILE_NAME);

        if (!file.exists()) {
            return records;
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

                    splitRecord(line);

                    records.add(line);

                } catch (RuntimeException e) {

                    throw new IOException(
                            "Invalid record at line " + lineNo, e
                    );
                }
            }
        }

        return records;
    }

    // ========== SAVE ALL RECORDS ==========

    static void saveRecords(ArrayList<String> records)
            throws IOException {

        try (BufferedWriter writer =
                     new BufferedWriter(new FileWriter(FILE_NAME))) {

            for (String record : records) {

                writer.write(record);
                writer.newLine();
            }
        }
    }
}
