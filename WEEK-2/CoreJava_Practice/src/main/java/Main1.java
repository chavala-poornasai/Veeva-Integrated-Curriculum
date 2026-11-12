class LoaderTest {
    static {
        System.out.println("LoaderTest Initialized");
    }
    public static final int VALUE = 100;
}

public class Main1 {
    public static void main(String[] args) {
        System.out.println(LoaderTest.VALUE);
    }
}
