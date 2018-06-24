package reactor;

import org.junit.Test;

import java.util.function.*;

/**
 * @author Lucifer
 * @do lamond表达式的测试
 * @date 2018/06/24 15:03
 */
public class LamondTest {

    @Test
    public void tset(){
        //断言函数接口
        //IntPredicate
        Predicate<Integer> predicate = i -> i > 0;
        System.out.println(predicate.test(-9));
        //消费函数接口
        //IntConsumer
        Consumer<String> consumer = c -> System.out.println(c);
        consumer.accept("消费函数接口");
    }

    @Test
    public void test2(){
        Consumer<String> consumer = System.out::println;
        consumer.accept("hello you !");
    }

    @Test
    public void dogSay(){
        //静态方法引用
        Consumer<Dog> consumer = Dog::sayHello;
        consumer.accept(new Dog());
        //非静态方法的实例引用
        /*Function<Integer, Integer> function = new Dog()::eat;
        System.out.println(function.apply(2));*/
        UnaryOperator<Integer> unaryOperator = new Dog()::eat;
        System.out.println(unaryOperator.apply(3));
    }

    @Test
    public void dogTest(){
        Supplier<Dog> supplier = Dog::new;
        System.out.println(supplier.get());
    }


}
class Dog{
    private String say = "hello";

    private int num = 10;

    public static void sayHello(Dog dog){
        System.out.println(dog);
    }

    public int eat(int nu){
        System.out.println("吃掉了" + nu + "个");
        return num -= nu;
    }

    @Override
    public String toString() {
        return "Dog{" +
                "say='" + say + '\'' +
                '}';
    }
}
