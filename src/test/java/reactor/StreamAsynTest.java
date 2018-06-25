package reactor;

import org.junit.Test;
import sun.jvm.hotspot.runtime.Threads;

import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * 并行流的测试
 *
 * @author Lucifer
 * @date 2018／06／26 00:39
 */
public class StreamAsynTest {

    @Test
    public void test(){
        //并行流的调用
        //IntStream.range(1, 100).parallel().peek(StreamAsynTest::sys).count();
        //同时调用并行流和串行流进行操作，执行后面的操作
        /*IntStream.range(1, 100)
                .parallel().peek(StreamAsynTest::sys)
                .sequential().peek(StreamAsynTest::sys1)
                .count();*/
        //并行流使用的线程是默认ForkJoinPool.commonPool-worker-2,数量等于当前机器的核心数量
        System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "20");
        IntStream.range(1, 100).parallel().peek(StreamAsynTest::sys).count();
    }

    public static void sys(int i){
        System.out.println(Thread.currentThread().getName() + " : " + i);
        try {
            Thread.sleep(3);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void sys1(int i){
        System.err.println(i);
        try {
            Thread.sleep(3);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
