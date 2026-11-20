import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class EqualsDemo {
    public static void main(String[] args) {
        // 1. Create two distinct user instances with identical data
        User user1 = new User("Alice", 25);
        User user2 = new User("Alice", 25);
        User user3 = new User("Bob", 30);

        // 2. Demonstrate Reference Equality (==) vs Content Equality (.equals())
        System.out.println("=== Equality Demonstration ===");
        System.out.println("user1 == user2: " + (user1 == user2)); // false (different memory locations)
        System.out.println("user1.equals(user2): " + user1.equals(user2)); // true (same name and age)
        System.out.println("user1.equals(user3): " + user1.equals(user3)); // false (different data)

        // 3. Demonstrate Hash Code values (they will match because data matches)
        System.out.println("\n=== Hash Code Demonstration ===");
        System.out.println("user1 hashCode: " + user1.hashCode());
        System.out.println("user2 hashCode: " + user2.hashCode());
        System.out.println("user3 hashCode: " + user3.hashCode());

        // 4. Demonstrate behavior in a Hash-Based Collection (HashSet)
        System.out.println("\n=== HashSet Behavior Demonstration ===");
        Set<User> userSet = new HashSet<>();

        userSet.add(user1);
        // Attempting to add user2 (identical data). HashSet uses hashCode() + equals() to check duplicates.
        userSet.add(user2);
        userSet.add(user3);

        // Size will be 2 because user2 was recognized as a duplicate and ignored
        System.out.println("Total unique users in Set: " + userSet.size());
        System.out.println("Set contains user2? " + userSet.contains(user2));
    }
}

class User {
    private String name;
    private int age;

    // Constructor to easily initialize data
    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return age == user.age && Objects.equals(name, user.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age);
    }
}
