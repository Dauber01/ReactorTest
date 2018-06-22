package reactor;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;

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

}
