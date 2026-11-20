 import java.util.Comparator;

public class NameComparator implements Comparator<NameComparable> {

    // Custom sorting strategy: Alphabetically by Name
    @Override
    public int compare(NameComparable u1, NameComparable u2) {
        return u1.getName().compareTo(u2.getName());
    }
}
