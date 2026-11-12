class Animal {}
class Dog extends Animal {
    void bark() {
        System.out.println("Woof!");
    }
}

public class instance {
    public static void main(String[] args) {
        Animal myAnimal = new Dog(); // Upcasting

        // 1. Verifying relationship
        System.out.println(myAnimal instanceof Dog);    // Prints: true
        System.out.println(myAnimal instanceof Animal); // Prints: true (Inheritance)

        // 2. Safe Downcasting
        if (myAnimal instanceof Dog) {
            Dog myDog = (Dog) myAnimal; // Safely downcast
            myDog.bark();
        }

        // 3. Handling null
        Animal emptyAnimal = null;
        System.out.println(emptyAnimal instanceof Dog); // Prints: false
    }
}
