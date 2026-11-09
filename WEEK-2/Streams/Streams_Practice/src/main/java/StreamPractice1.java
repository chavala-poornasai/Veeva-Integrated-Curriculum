import java.sql.SQLOutput;
import java.util.Locale;
import java.util.function.Predicate;

public class StreamPractice1 {
    public static void main(String[] args){
        Predicate<String> startsWithA = new Predicate<String>() {
            @Override
            public boolean test(String s) {
                return s.toLowerCase().startsWith("a");
            }
        };
        Predicate<String> stwithB=s->s.toLowerCase().startsWith("b");

// To use it:
        System.out.println(startsWithA.test("apple")); // returns true
        System.out.println(stwithB.test("pple"));
    }
}