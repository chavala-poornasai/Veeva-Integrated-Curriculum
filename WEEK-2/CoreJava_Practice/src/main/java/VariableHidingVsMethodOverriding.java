
// Parent class
class Base {

    // Instance variable of Base class
    int value = 10;

    // Method of Base class
    int getValue() {
        return value;
    }
}

// Child class extending Base
class Derived extends Base {

    // Hides the instance variable of Base class
    int value = 20;

    // Overrides the getValue() method of Base class
    @Override
    int getValue() {
        return value;
    }
}

// Main class
public class VariableHidingVsMethodOverriding {

    public static void main(String[] args) {

        // Parent reference pointing to Child object
        Base obj = new Derived();

        // Variable access is determined by reference type (Base)
        // Method execution is determined by actual object type (Derived)
        System.out.println(obj.value + " " + obj.getValue());
    }
}
