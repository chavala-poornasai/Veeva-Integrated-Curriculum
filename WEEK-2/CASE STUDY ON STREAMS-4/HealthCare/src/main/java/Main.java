
import java.util.*;
import java.util.stream.*;

public class Main {
    public static void main(String[] args) {

        List<Patient> patients = Arrays.asList(

                new Patient(1, "Sree", "Cardiology", "Dr.Ravi",
                        "Heart Attack", "Surgery", "ICU", 120, 150, 180, 5),

                new Patient(2, "Ram", "Neurology", "Dr.Kiran",
                        "Migraine", "Medication", "Outward", 85, 120, 110, 2),

                new Patient(3, "John", "Orthopedics", "Dr.Kumar",
                        "Fracture", "Plaster", "Inward", 105, 140, 150, 4),

                new Patient(4, "David", "Cardiology", "Dr.Ravi",
                        "Chest Pain", "Medication", "ICU", 110, 145, 160, 5),

                new Patient(5, "Arun", "Neurology", "Dr.Kiran",
                        "Stroke", "Treatment", "Inward", 95, 130, 140, 3),

                new Patient(6, "Kiran", "Orthopedics", "Dr.Kumar",
                        "Back Pain", "Physiotherapy", "Outward", 90, 120, 100, 1)
        );

        // 1. Find all patients in ICU
        System.out.println("Patients in ICU:");

        patients.stream()
                .filter(p -> p.patient_type.equals("ICU"))
                .forEach(System.out::println);


        // 2. Find ICU patients with elevated heart rate (> 100)
        System.out.println("\nICU Patients with Elevated Heart Rate:");

        patients.stream()
                .filter(p -> p.patient_type.equals("ICU"))
                .filter(p -> p.heart_rate > 100)
                .forEach(System.out::println);


        // 3. Sort patients by severity (Highest first)
        System.out.println("\nPatients Sorted by Severity:");

        patients.stream()
                .sorted(Comparator.comparingInt(
                        (Patient p) -> p.severity
                ).reversed())
                .forEach(System.out::println);
    }
}
