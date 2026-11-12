
import java.util.function.Consumer;
class LambdaTest {
    int var1=200;
    public void process() {
        final int count = 10;
        Consumer<Integer> consumer = x -> {
            System.out.println(count+var1);
        };
        consumer.accept(5);
        var1--;
        consumer.accept(5);
    }
}
class Execute{
    public static void main(String args[]){
        LambdaTest obj = new LambdaTest();
        obj.process();
    }
}