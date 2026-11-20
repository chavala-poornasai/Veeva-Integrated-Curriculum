import java.util.Objects;

// 1. Updated the interface type to match the class name
public class NameComparable implements Comparable<NameComparable> {
    private String name;
    private int age;

    public NameComparable(String name, int age) {
        this.name = Objects.requireNonNull(name, "Name cannot be null");
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    // 2. This now correctly matches the interface signature above
    @Override
    public int compareTo(NameComparable other) {
        return Integer.compare(this.age, other.age);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        // 3. Changed the cast from (User) to (NameComparable)
        NameComparable other = (NameComparable) o;
        return age == other.age && Objects.equals(name, other.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age);
    }

    @Override
    public String toString() {
        return name + " (" + age + ")";
    }
}
