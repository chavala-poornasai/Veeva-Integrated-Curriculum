import java.net.SocketOption;
import java.util.*;

public class Stprac {
    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        ArrayList<ArrayList<String>> al = new ArrayList<>();
        System.out.println("Enter the Character");
        char c = sc.next().charAt(0);
        al.add(new ArrayList<>(Arrays.asList("C","Cpp","Java","Python","jocata","C","Java")));
        al.add(new ArrayList<>(Arrays.asList("SQL","My-sql","Oracle","Postgress","SQL")));
        al.add(new ArrayList<>(Arrays.asList("Windows","Mac","Linux","unix","Mac")));
        al.add(new ArrayList<>(Arrays.asList("NotebookLLM","CHATGPT","GEMINI","CLAUDE","GEMINI","CLAUDE")));
        // 1.  Starting with character -C
//        al.stream()
//                    .flatMap(List::stream)
//                    .filter(n->n.startsWith(c+""))
//                .forEach(System.out::println);
        // 2. starting with 'j' and ending with 'a'
//        al.stream()
//                    .flatMap(List::stream)
//                    .filter(n->(n.startsWith("j") && n.endsWith("a")))
//                    .forEach(System.out::println);
//
        // 3. starts with specific letter and but in uppercase
//        al.stream()
//                    .flatMap(List::stream)
//                    .filter(n->(n.startsWith(c+"") && Character.isUpperCase(n.charAt(0))))
//                    .forEach(System.out::println);

        // 4. starts with specific letter and but in  uppercase and distinct

//        al.stream()
//                .flatMap(List::stream)
//                .filter(n->(n.startsWith(c+"") && Character.isUpperCase(n.charAt(0))))
//                .distinct()
//                .forEach(System.out::println);
        //5.starts with specific letter and in sorted

//        al.stream()
//                .flatMap(List::stream)
//                .filter(n->(n.startsWith(c+"")))
//                .sorted()
//                .forEach(System.out::println);

        //6. all the distinct letter starting with specific letter in uppercase and in sorted order

//        al.stream()
//                .flatMap(List::stream)
//                .filter(n->(n.startsWith(c+"") && Character.isUpperCase(n.charAt(0))))
//                .sorted()
//                .forEach(System.out::println);

        //7. count of distinct content starts with specific letter
//
//        long res = al.stream()
//                .flatMap(List::stream)
//                .filter(n->(n.startsWith(c+"")))
//                .distinct()
//                .count();
//        System.out.println(res);

        //8. first element starts wth given character
//        Optional<String> res = al.stream()
//                .flatMap(List::stream)
//                .filter(n->n.startsWith(c+""))
//                .findFirst();
//        System.out.println(res);

        //9. check whether any element starts with 's'
        Optional<String> res=al.stream()
                .flatMap(List::stream)
                .filter(n->n.startsWith(c+""))
                .findAny();
        System.out.println(res.isPresent());




    }

}
