import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String args[])
    {
        //Scanner sc = new Scanner(System.in);
        List<Employees> EmpList = new ArrayList<>();
        EmpList.add(new Employees(101,"Srinu","IT",28));
        EmpList.add(new Employees(102,"Vasu","IT",34));
        EmpList.add(new Employees(103,"Sidhu","HR",47));
        EmpList.add(new Employees(104,"Mark","HR",41));
        EmpList.add(new Employees(105,"Harsha","Finance",36));
        EmpList.add(new Employees(106,"sourya","Finance",31));
        EmpList.add(new Employees(107,"sailesh","Developer",25));
        EmpList.add(new Employees(108,"Bala","SDET",27));
        EmpList.add(new Employees(109,"Poorna","SDET",22));
        EmpList.add(new Employees(110,"Sriram","Developer",35));


        // 1. Grouping the Employees based on age
//        Map<String, List<Employees>> result = EmpList.stream()
//                .collect(Collectors.groupingBy(emp -> {
//
//                    int age = emp.getAge();
//                    int bucketSize =5;
//                    int lower = (age / bucketSize) * bucketSize;
//                    int upper = lower + bucketSize;
//
//                    return lower + "-" + upper;
//                }));
//
//        result.forEach((range, employees) -> {
//            System.out.println(range + " : " + employees);
//        });


        // 2. Find oldest Employee
//        Optional<Employees> OName = EmpList.stream()
//                                            .max(Comparator.comparingInt(Employees ::getAge));
//
//        if(OName.isPresent()) System.out.println(OName.get());
//        Optional<Employees> Name = EmpList.stream()
//                                            .min(Comparator.comparingInt(Employees ::getAge));
//
//        if(Name.isPresent()) System.out.println(Name.get());


    }

}
