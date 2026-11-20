import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class LamComp {
    public static void main(String[] args) {
        List<NameComparable> users = new ArrayList<>();
        users.add(new NameComparable("Charlie", 30));
        users.add(new NameComparable("Alice", 25));
        users.add(new NameComparable("Bob", 22));

        System.out.println("Original List: " + users);

        // Approach 1: Standard Lambda Expression
        // Syntax: (parameters) -> { body }
        users.sort((u1, u2) -> u1.getName().compareTo(u2.getName()));
        System.out.println("Sorted by Name (Standard Lambda): " + users);

        // Approach 2: Using Comparator.comparing() with a Method Reference (Cleanest)
        // User::getName points directly to the getter method of the property you want to sort by
        users.sort(Comparator.comparing(NameComparable::getName));
        System.out.println("Sorted by Name (Method Reference): " + users);

        // Approach 3: Reversing the order (e.g., Alphabetically Descending)
        users.sort(Comparator.comparing(NameComparable::getName).reversed());
        System.out.println("Sorted by Name (Reversed / Descending): " + users);

        // Approach 4: Sorting by Age Descending (Oldest to Youngest)
        users.sort(Comparator.comparingInt(NameComparable::getAge).reversed());
        System.out.println("Sorted by Age (Oldest first): " + users);
    }
}
