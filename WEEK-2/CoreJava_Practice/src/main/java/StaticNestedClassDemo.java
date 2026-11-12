
class Outer {

    private int instanceVar = 10;
    private static int staticVar = 20;

    // Static nested class
    static class StaticNested {

        void display() {

            System.out.println("Accessing Outer Class Members:");

            // Accessing static variable directly
            System.out.println("Static Variable: " + staticVar);

            // Accessing instance variable using Outer object
            Outer obj = new Outer();
            System.out.println("Instance Variable: " + obj.instanceVar);
        }

        void displayStaticOnly() {
            System.out.println("Static Variable: " + staticVar);
        }
    }

    void outerDisplay() {
        System.out.println("Outer Instance Method");
    }
}

public class StaticNestedClassDemo {

    public static void main(String[] args) {

        System.out.println("Creating Static Nested Class Object");

        // No Outer object is required
        Outer.StaticNested nestedObj =
                new Outer.StaticNested();

        nestedObj.display();

        System.out.println("\nAccessing Only Static Member:");

        nestedObj.displayStaticOnly();
    }
}
