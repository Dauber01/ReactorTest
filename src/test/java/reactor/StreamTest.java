package reactor;

import org.junit.Test;

import java.util.stream.IntStream;

/**
 * 对spring boot 2.0流的特性进行测试
 *
 * @author Lucifer
 * @date 2018／06／25 19:35
 */
public class StreamTest {

    @Test
    public void add(){
        int[] nums = {1, 2, 3, 4};
        //假如结果返回的是流则为中间操作，如果返回的是结果，则为终止操作
        int result = IntStream.of(nums).map(StreamTest::multiply).sum();
        System.out.println(result);
        //惰性求值,即在没有最终知行的情况下中间的过程不会被执行
        IntStream.of(nums).map(StreamTest::multiply);
    }

    public static int multiply(int i){
        System.out.println("乘二操作：" + i);
        return 2 * i;
    }

}
