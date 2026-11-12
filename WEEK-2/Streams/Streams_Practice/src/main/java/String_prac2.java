import java.util.*;
import java.util.stream.Collectors;

public class String_prac2 {
    public static void main(String[] args) {

        List<String> word = new ArrayList<>(Arrays.asList("Hii","hello","this","is","poorna" ,"sai","Hii","poorna"));

        //1. Repeated Words
//        List<String> ReUsedWord = word.stream()
//                                        .collect(Collectors.groupingBy(n->n ,Collectors.counting()))
//                                        .entrySet()
//                                        .stream()
//                                        .filter(entry->entry.getValue()>1)
//                                        .map(Map.Entry :: getKey)
//                                        .collect(Collectors.toList());
//
//        System.out.println("Repeated words : "+ReUsedWord);

        //2. Find First Repeated word

//                        List<String> ReUsedWord = word.stream()
//                                        .collect(Collectors.groupingBy(n->n ,Collectors.counting()))
//                                        .entrySet()
//                                        .stream()
//                                        .filter(entry->entry.getValue()>1)
//                                        .map(Map.Entry :: getKey)
//                                        .collect(Collectors.toList());
//
//
//                      if(ReUsedWord.size()>0)  System.out.println("FIrst Repeated word : "+ReUsedWord.get(0));


        // first  repeating word
//        Optional<String> firstRepeated = word.stream()
//                .collect(Collectors.groupingBy(
//                        n -> n,
//                        LinkedHashMap::new,
//                        Collectors.counting()
//                ))
//                .entrySet()
//                .stream()
//                .filter(entry -> entry.getValue() > 1)
//                .map(Map.Entry::getKey)
//                .findFirst();
//
//        if(firstRepeated.isPresent()) System.out.println(firstRepeated.get());

        // Map the Strings to their length
//        List<String> res =word.stream()
//                .collect(Collectors.toMap(
//                        n -> n,
//                        String::length,
//                        (existing,replacement)->existing,
//                        LinkedHashMap::new
//                ))
//                .entrySet()
//                .stream()
//                .map(entry->(entry.getKey()+entry.getValue()))
//                .toList();
//        System.out.println(res);
        // longest word
//                    List<String> LongWord= word.stream()
//                            .collect(Collectors.toMap(
//                                    str->str,
//                                    String :: length,
//                                    (existing,replacement)->existing,
//                                    LinkedHashMap::new
//                            ))
//                            .entrySet()
//                            .stream()
//                            .max(Comparator.comparingInt(Map.Entry::getValue))
//                            .stream()
//                            .map(entry->entry.getKey())
//                            .toList();
//        System.out.println("Longest Word in the list is "+ LongWord.getFirst());
        // shortest word
//        Optional<String> shortWord= word.stream()
//                .collect(Collectors.toMap(
//                        str->str,
//                        String :: length,
//                        (existing,replacement)->existing,
//                        LinkedHashMap::new
//                ))
//                .entrySet()
//                .stream()
//                .min(Comparator.comparingInt(Map.Entry::getValue))
//                .stream()
//                .map(entry->entry.getKey())
//                .findFirst();
//        System.out.println("Shortest Word in the list is :- "+ shortWord.get());


    }

}
