import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<NameComparable> users = new ArrayList<>();
        users.add(new NameComparable("Charlie", 30));
        users.add(new NameComparable("Alice", 25));
        users.add(new NameComparable("Bob", 22));

        System.out.println("Original List:");
        System.out.println(users);

        // 1. Sorting using Comparable (Natural Order: Age)
        // Collections.sort looks for the compareTo() method inside User.java
        Collections.sort(users);
        System.out.println("\nSorted by Age (Natural Order via Comparable):");
        System.out.println(users);

        // 2. Sorting using Comparator (Custom Order: Name)
        // We pass the external NameComparator strategy into the sort method
        Collections.sort(users, new NameComparator());
        System.out.println("\nSorted Alphabetically by Name (via Comparator):");
        System.out.println(users);
    }
}
