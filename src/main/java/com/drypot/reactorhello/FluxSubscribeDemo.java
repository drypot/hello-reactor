package com.drypot.reactorhello;

import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;

public class FluxSubscribeDemo {

    public static void main(String[] args) {
        runSampleSubscriber2();
    }

    private static void runSubscribe() {
        Flux<Integer> ints = Flux.range(1, 3);
        ints.subscribe();
    }

    private static void runSubscribe1() {
        Flux<Integer> ints = Flux.range(1, 3);
        ints.subscribe(System.out::println);
    }

    private static void runSubscribe2() {
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

    private static void runSubscribe1b() {
        Flux<Integer> ints = Flux.range(1, 4)
            .map(i -> {
                if (i < 3) return i;
                throw new RuntimeException("Got to 3");
            });
        ints.subscribe(
            System.out::println
        );
    }

    private static void runSubscribe3() {
        Flux<Integer> ints = Flux.range(1, 4);
        ints.subscribe(
            System.out::println,
            System.err::println,
            () -> { System.out.println("Done"); }
        );
    }

    private static void runSampleSubscriber() {
        Flux<Integer> ints = Flux.range(1, 4);
        ints.subscribe(
            System.out::println,
            System.err::println,
            () -> { System.out.println("Done"); }
        );
        ints.subscribe(
            new BaseSubscriber<Integer>() {
                @Override
                protected void hookOnSubscribe(Subscription subscription) {
                    System.out.println("subscribed");

                    //request(1);
                    requestUnbounded();
                }

                @Override
                protected void hookOnNext(Integer value) {
                    System.out.println("::" + value);

                    //request(1);
                    //cancel();
                }
            }
        );
        System.out.println("aaa");
    }

    private static void runSampleSubscriber2() {
        Flux<Integer> ints = Flux.range(1, 4);
        ints.subscribe(new BaseSubscriber<Integer>() {
            @Override
            protected void hookOnSubscribe(Subscription subscription) {
                System.out.println("subscribed");
                requestUnbounded();
            }

            @Override
            protected void hookOnNext(Integer value) {
                System.out.println("a:" + value);
                if (value.intValue() == 3) {
                    cancel();
                }
            }
        });
        ints.subscribe(new BaseSubscriber<Integer>() {
            @Override
            protected void hookOnSubscribe(Subscription subscription) {
                System.out.println("subscribed");
                requestUnbounded();
            }

            @Override
            protected void hookOnNext(Integer value) {
                System.out.println("b:" + value);
            }
        });
        System.out.println("aaa");
    }

}

