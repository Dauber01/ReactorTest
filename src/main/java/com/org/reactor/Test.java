package com.org.reactor;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author Lucifer
 * @do
 * @date 2018/06/22 14:14
 */
public class Test {

    public static void main(String[] args){
        Flux.just(1, 2, 3, 4, 5).subscribe(System.out::print);
        System.out.println();
        Mono.just(1).subscribe(System.out::print);
    }

}
