class Parent1 {
    static int x = initialize("Parent static field x");
    int y = initialize("Parent instance field y");

    static {
        System.out.println("Parent static block");
    }

    Parent1() {
        System.out.println("Parent constructor");
    }

    static int initialize(String msg) {
        System.out.println(msg);
        return 1;
    }
}

class Child1 extends Parent1 {
    static int a = initialize("Child static field a");
    int b = initialize("Child instance field b");

    static {
        System.out.println("Child static block");
    }

    Child1() {
        System.out.println("Child constructor");
    }
}

public class intialization_order {
    public static void main(String[] args) {
        Child1 c = new Child1();
    }
}
