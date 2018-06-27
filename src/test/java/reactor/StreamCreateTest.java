package reactor;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * 基础流的创建测试
 * @author Lucifer
 * @date 2018／06／25 22:18
 */
public class StreamCreateTest {

    @Test
    public void streamCreate(){
        List<String> list = Arrays.asList("hah", "heihei", "hehe");
        //从集合创建
        list.stream();
        list.parallelStream();
        //从数组创建
        Arrays.stream(new int[]{1, 2, 3, 4});
        //从数字创建
        IntStream.of(1, 2, 3);
        IntStream.range(1, 10);
        //从random创建一个无限流
        new Random().ints().limit(10);
        //自己定义创建一个流
        Random random = new Random();
        Stream.generate(() -> random.nextInt()).limit(20);
    }

}
