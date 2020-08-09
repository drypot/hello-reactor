package com.drypot.reactorhello;

import reactor.core.publisher.Flux;

public class FluxGenerateDemo {

    public static void main(String[] args) {
        runGenerator();
    }

    private static void runGenerator() {
        Flux<String> f = Flux.generate(
            () -> 0,
            (state, sink) -> {
                sink.next("3 x " + state + " = " + 3*state);
                if (state == 10) sink.complete();
                return state + 1;
            },
            (state) -> System.out.println(state)
        );
        f.subscribe(System.out::println);
    }

}
