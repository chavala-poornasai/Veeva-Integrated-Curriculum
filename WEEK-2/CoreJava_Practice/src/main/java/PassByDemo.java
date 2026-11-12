public class PassByDemo {
    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder("Veeva");
        modify(sb);
        System.out.println(sb);
    }
    public static void modify(StringBuilder s) {
        s.append("SDET");
        s = new StringBuilder("QA");
    }
}
