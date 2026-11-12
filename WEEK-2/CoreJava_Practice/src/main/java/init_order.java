class Parent {
    static { System.out.print("A"); }
    { System.out.print("B"); }
    public Parent() { System.out.print("C"); }
}
class Child extends Parent {
    static { System.out.print("D"); }
    { System.out.print("E"); }
    public Child() { System.out.print("F"); }
}
public class init_order {
    public static void main(String[] args) {
        new Child();
    }
}
