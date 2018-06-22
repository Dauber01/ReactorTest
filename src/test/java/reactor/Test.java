package reactor;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author Lucifer
 * @do
 * @date 2018/06/22 14:14
 */
public class Test {

    @org.junit.Test
    public void test1(){
        Flux.just(1, 2, 3, 4, 5).subscribe(System.out::print);
        System.out.println();
        Mono.just(1).subscribe(System.out::print);
    }

    @org.junit.Test
    public void test2(){
        Flux.just(1, 2, 3, 4, 5).subscribe(
                System.out::println,
                System.err::println,
                () -> System.out.println("Completed")
        );
    }

    @org.junit.Test
    public void test3(){
        Mono.error(new Exception("is error")).subscribe(
            System.out::println,
            System.err::println,
                () -> System.out.println("Completed")
        );
    }

}
