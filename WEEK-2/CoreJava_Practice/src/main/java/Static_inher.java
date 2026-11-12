class Super {
    public static String name = "SuperClass";
    public static void printName() {
        System.out.println("Static Super");
    }
}
class Sub extends Super {
    public static String name = "SubClass";
    public static void printName() {
        System.out.println("Static Sub");
    }
}
public class Static_inher {
    public static void main(String[] args) {
        Super obj = new Sub();
        System.out.println(obj.name);
        obj.printName();
    }
}
