package reactor;

import org.junit.Test;
import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.SignalType;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

import java.lang.reflect.Type;
import java.time.Duration;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;

/**
 * @author Lucifer
 * @do 测试简单的测试原件,StepVerify
 * @date 2018/06/22 14:36
 */
public class StepVerifierTest {

    private Flux<Integer> generateFluxFrom1To(){
        return Flux.just(1, 2, 3, 4, 5);
    }

    private Mono<Integer> generateMonoWithError(){
        return Mono.error(new Exception("error Exception discribe ~"));
    }

    @Test
    public void testViaStepVerifier(){
        StepVerifier.create(generateFluxFrom1To())
                .expectNext(1, 2, 3, 4, 5)
                .expectComplete()
                .verify();
        StepVerifier.create(generateMonoWithError())
                .expectErrorMessage("error Exception discribe ~")
                .verify();
    }

    @Test
    public void testFlux(){
        StepVerifier.create(Flux.range(1, 6)
            .map(i -> i * i)).expectNext(1, 4, 9, 16, 25, 36)
                .verifyComplete();
    }

    @Test
    public void testFlatMap(){
        StepVerifier.create(Flux.just("flux", "mono")
            .flatMap(s -> Flux.fromArray(s.split("//s+")).delayElements(Duration.ofMillis(100)))
            .doOnNext(System.out::print))
                .expectNextCount(8)
                .verifyComplete();
    }

    private String getStringSync(){
        try {
            TimeUnit.SECONDS.sleep(2);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return "Hello sync string";
    }

    @Test
    public void testSyncToAsyn() throws InterruptedException{
        CountDownLatch countDownLatch = new CountDownLatch(1);
        Mono.fromCallable(() -> getStringSync())
                .subscribeOn(Schedulers.elastic())
                .subscribe(System.out::println, null, countDownLatch::countDown);
        countDownLatch.await(10, TimeUnit.SECONDS);
    }

    @Test
    public void testException(){
        Flux.range(1, 6)
                .map(i -> 10/(i-3))
                .onErrorReturn(0)
                .map(i -> i * i)
                .subscribe(System.out::println, System.err::println);
    }

    @Test
    public void testException1(){
        Flux.range(1, 6)
                .map(i -> 10/(i-3))
                .onErrorResume(e -> Mono.just(new Random().nextInt(6)))
                .map(i -> i * i)
                .subscribe(System.out::println, System.err::println);
        /*Flux.just(endpoint1, endpoint2)
                .flatMap(k -> callExternalService(k))
                .onErrorResume(e -> getFromCache(k));*/
    }

    @Test
    public void testException2(){
        Flux.just("time out")
                .map(e -> 10/0)
                .onErrorMap(e -> new RuntimeException("haha", e));
    }

    @Test
    public void testException3(){
       /* Flux.just("1", "2")
                .map(e -> 10/0)
                .doOnError(e -> {
                    log("the error is:", e);
                })
                .onErrorResume(e -> getFromCache(k));*/
    }

    @Test
    public void testException4(){
        /*Flux.using(
                () -> getResuce(),
                e -> Flux.just(e.getClass()),
                Mysouce::clean
        );*/
    }

    @Test
    public void testDoFinally(){
        LongAdder longAdder = new LongAdder();
        Flux.just("1", "2")
                .doFinally(type -> {
                            if (type == SignalType.CANCEL){
                                longAdder.increment();
                            }
                        })
                .take(1);
    }

    @Test
    public void testReTry() throws InterruptedException{
        Flux.range(1, 6)
                .map(i -> 10/(i-3))
                .retry(1)
                .subscribe(
                        System.out::println,
                        System.err::println
                );
                Thread.sleep(100);
    }

    @Test
    public void testBackpressure(){
        Flux.range(1, 6)
                .doOnRequest(count -> System.out.println("the" + count + "time"))
                .subscribe(new BaseSubscriber<Integer>() {
                    @Override
                    protected void hookOnSubscribe(Subscription subscription) {
                        System.out.println("a request is begin");
                        request(1);
                    }

                    @Override
                    protected void hookOnNext(Integer value) {
                        try{
                            Thread.sleep(1);
                        }catch (InterruptedException e){
                            e.printStackTrace();
                        }
                        System.out.println("the value is :" + value);
                        request(1);
                    }
                });
    }

}
