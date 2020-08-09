package com.drypot.reactorhello;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

public class FluxSchedulerDemo {

    public static void main(String[] args) throws InterruptedException {
        runPublishOnDemo();
    }

    private static void runMono1() throws InterruptedException {
        final Mono<String> mono = Mono.just("hello ");

        Thread t = new Thread(() -> mono
            .map(msg -> msg + "thread ")
            .subscribe(v ->
                System.out.println(v + Thread.currentThread().getName())
            )
        );
        t.start();
        t.join();
    }

    private static void runPublishOnDemo() throws InterruptedException {
        Scheduler s = Schedulers.newParallel("parallel-scheduler", 4);

        final Flux<Integer> flux = Flux
            .range(1, 4)
            .map(i -> {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                printThreadName("a: " + i);
                return i;
            })
            .publishOn(s)
            .map(i -> {
                printThreadName("b: " + i);
                return i;
            });

        printThreadName("s: ");

        new Thread(() -> {
            printThreadName("c: ");
            flux.subscribe((r) -> {
                printThreadName("d: " + r);
            });
        }).start();
    }

    private static void printThreadName(String s) {
        System.out.println(s + " @" + Thread.currentThread().getName());
    }
}

