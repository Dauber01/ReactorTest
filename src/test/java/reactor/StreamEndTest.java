package reactor;

import org.junit.Test;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 流的结束测试
 *
 * @author Lucifer
 * @date 2018／06／25 23:26
 */
public class StreamEndTest {

    @Test
    public void test(){
        String str = "this is a new world";
        //forEachOrder保证并行时候返回数据的顺序
        str.chars().parallel().forEach(i -> System.out.print((char)i));
        System.out.println("分割-----");
        str.chars().parallel().forEachOrdered(i -> System.out.print((char)i));
        //收集操作
        List<String> strings = Stream.of(str.split(" ")).collect(Collectors.toList());
        System.out.println(strings);
        //reduce操作
        Optional<String> result = Stream.of(str.split(" ")).reduce((a, b) -> a + "|" +b);
        System.out.println(result.orElse(""));
        Integer integer = Stream.of(str.split(" ")).map(s -> s.length()).reduce(0, (a, b) -> a + b);
        System.out.println(integer);
        //max的使用
        Optional<String> res = Stream.of(str.split(" ")).max((a, b) -> a.length() - b.length());
        System.out.println(res.get());
        //终止操作
        OptionalInt in = new Random().ints().findFirst();
        System.out.println(in);
    }

    @Test
    public void test1(){

    }

}
