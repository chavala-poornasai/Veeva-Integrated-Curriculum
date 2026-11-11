import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        ArrayList<Integer> al = new ArrayList<>(Arrays.asList(10,20,37,30,40,47,50,60,70,77,60,70,70,80,90,91,97,100));
        ArrayList<Integer> bl = new ArrayList<>(Arrays.asList(1,10,1,2,2,3,3,4,50,6,7,11,8,8,9,10));
        //Scanner sc = new Scanner(System.in);
        // 1. to find the sum of al and bl arrayList individually
//        int res = al.stream()
//                .reduce(0,(a,b)->a+b);
//        System.out.println(res);
//        int res1 = bl.stream()
//                .reduce(0,(a,b)->a+b);
//        System.out.println(res1);

        // 1.2. to find the avg of al and bl arrayList individually

//        int avg;
//        avg = res/al.size();
//        System.out.println("Average :" + avg);

        //1.3. Max element present in the List

//       int number = al.stream()
//                                    .max(Comparator.naturalOrder()).orElse(-1);
//        System.out.println(number);
//
//
//        Optional<Integer> res = al.stream()
//                                    .max(Comparator.comparing(Integer::intValue));
//        if (res.isPresent()) System.out.println(res.get());


        //1.4. Min Element
//        System.out.println("al List");
//        int num = al.stream()
//                .min(Comparator.naturalOrder()).orElse(-1);
//        System.out.println(num);
//
//
//        Optional<Integer> result = al.stream()
//                .min(Comparator.comparing(Integer::intValue));
//        if (result.isPresent()) System.out.println(result.get());
//
//
//        System.out.println("bl List");
//        int num2 = bl.stream()
//                .min(Comparator.naturalOrder()).orElse(-1);
//        System.out.println(num2);
//
//
//        Optional<Integer> result2 = bl.stream()
//                .min(Comparator.comparing(Integer::intValue));
//        if (result2.isPresent()) System.out.println(result2.get());

        //2. top k most frequent element from al and bl list.

//        System.out.println("Enter the K value ");
//        int k = sc.nextInt();
//        List<Integer> ResList = al.stream()
//                .collect(Collectors.groupingBy(n->n,Collectors.counting()))
//                .entrySet()
//                .stream()
//                .sorted(Map.Entry.<Integer,Long>comparingByValue().reversed())
//                .limit(k)
//                .map(Map.Entry::getKey)
//                .collect(Collectors.toList());
//        System.out.println(ResList);
//        List<Integer> ResList1 = bl.stream()
//                .collect(Collectors.groupingBy(n->n,Collectors.counting()))
//                .entrySet()
//                .stream()
//                .sorted(Map.Entry.<Integer,Long>comparingByValue().reversed())
//                .limit(k)
//                .map(Map.Entry::getKey)
//                .collect(Collectors.toList());
//        System.out.println(ResList1);

        // 3. find 2nd most frequent element
//                    Optional<Integer> res = al.stream()
//                                .collect(Collectors.groupingBy(n->n,Collectors.counting()))
//                                .entrySet()
//                                .stream()
//                                .sorted(Map.Entry.<Integer,Long>comparingByValue().reversed())
//                                .skip(1)
//                                .map(Entry->Entry.getKey())
//                                .findFirst();
//                    if(res.isPresent()) System.out.println(res.get());
//
//
//        Optional<Integer> res1 = bl.stream()
//                .collect(Collectors.groupingBy(n->n,Collectors.counting()))
//                .entrySet()
//                .stream()
//                .sorted(Map.Entry.<Integer,Long>comparingByValue().reversed())
//                .skip(1)
//                .map(Entry->Entry.getKey())
//                .findFirst();
//
//        if(res1.isPresent()) System.out.println(res1.get());


        // 4. merge 2 list using flat map
//        ArrayList<ArrayList<Integer>> NestList = new ArrayList<>();
//        NestList.add(al);
//        NestList.add(bl);
//        NestList.stream()
//                .flatMap(List::stream)
//                .forEach(System.out::println);


        //5. find common elements btw lists using streams

//        List<Integer> result = al.stream()
//                .filter(bl::contains)
//                 .distinct()
//                .collect(Collectors.toList());
//
//        System.out.println(result);

        // 6. list of streams and map each string and stringValue
        //7. find all the prime numbers using streams

//        List<Integer> PrimeList = al.stream()
//                                    .filter(n-> {
//                                        if(n == 1 || n == 0) return false;
//                                        if(n==2) return true;
//                                        for(int i=2;i<=n/2;i++)
//                                        {
//                                            if(n%i==0)
//                                                return false;
//                                        }
//                                        return true;
//                                    })
//                                    .distinct()
//                                    .toList();
//
//
//        System.out.println("Prime Numbers List : "+PrimeList);
//        List<Integer> PrimeList2 = bl.stream()
//                .filter(n-> {
//                    if(n == 1 || n == 0) return false;
//                    if(n == 2) return true;
//                    for(int i=2;i<=n/2;i++)
//                    {
//                        if(n%i==0)
//                            return false;
//                    }
//                    return true;
//                })
//                .distinct()
//                .toList();
//
//        System.out.println("Prime Numbers List : "+PrimeList2);

        // 8.  find all the non primes
//        List<Integer> PrimeList = al.stream()
//                                    .filter(n-> {
//                                        if(n<0) return true;
//                                        if(n == 1 || n == 0) return true;
//                                        if(n==2) return false;
//                                        for(int i=2;i<=n/2;i++)
//                                        {
//                                            if(n%i==0)
//                                                return true;
//                                        }
//                                        return false;
//                                    })
//                                    .distinct()
//                                    .toList();
//
////
//        System.out.println("Non-Prime Numbers List : "+PrimeList);
//        List<Integer> PrimeList2 = bl.stream()
//                .filter(n-> {
//                    if(n<0) return true;
//                    if(n == 1 || n == 0) return true;
//                    if(n == 2) return false;
//                    for(int i=2;i<=n/2;i++)
//                    {
//                        if(n%i==0)
//                            return true;
//                    }
//                    return false;
//                })
//                .distinct()
//                .toList();
//
//        System.out.println("Non-Prime Numbers List : "+PrimeList2);

        // 9. find first repeating character



    }
}
