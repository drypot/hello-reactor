package com.drypot.reactorhello;

import reactor.core.publisher.Flux;

public class FluxExample1 {

    public static void main(String[] args) {
        run4();
    }

    private static void run1() {
        Flux<Integer> ints = Flux.range(1, 3);
        ints.subscribe();
    }

    private static void run2() {
        Flux<Integer> ints = Flux.range(1, 3);
        ints.subscribe(System.out::println);
    }

    private static void run3() {
        Flux<Integer> ints = Flux.range(1, 4)
            .map(i -> {
                if (i < 3) return i;
                throw new RuntimeException("Got to 3");
            });
        ints.subscribe(
            System.out::println,
            System.err::println
        );
    }

    private static void run4() {
        Flux<Integer> ints = Flux.range(1, 4);
        ints.subscribe(
            System.out::println,
            System.err::println,
            () -> { System.out.println("Done"); }
        );
    }

}
