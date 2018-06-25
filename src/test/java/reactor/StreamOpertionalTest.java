package reactor;

import org.junit.Test;

import java.util.Random;
import java.util.stream.Stream;

/**
 * stream的中间操作测试
 *
 * @author Lucifer
 * @date 2018／06／25 22:53
 */
public class StreamOpertionalTest {

    @Test
    public void test(){
        String str = "this is a new word";
        //把单词的长度掉出来
        Stream.of(str.split(" ")).filter(s -> s.length() > 2).map(s -> s.length()).forEach(System.out::println);
        //flatMap适用于A元素的B属性
        Stream.of(str.split(" ")).flatMap(s -> s.chars().boxed()).forEach(
                s -> System.out.println((char)s.intValue())
        );
        //peek用于debug模式,是个中间操作，和foreach同为终止操作
        System.out.println("分隔符-------------");
        Stream.of(str.split(" ")).peek(System.out::println).forEach(System.out::println);
        //limit的使用,主要用于无限流
        new Random().ints().filter(i -> i > 100 && i < 120).limit(10).forEach(System.out::println);
    }

}
