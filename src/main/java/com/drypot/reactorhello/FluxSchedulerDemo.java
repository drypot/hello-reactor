package com.drypot.reactorhello;

import reactor.core.publisher.Mono;

public class FluxSchedulerDemo {

    public static void main(String[] args) {
        runMono1();
    }

    private static void runMono1() {
        final Mono<String> mono = Mono.just("hello ");

        Thread t = new Thread(() -> mono
            .map(msg -> msg + "thread ")
            .subscribe(v ->
                System.out.println(v + Thread.currentThread().getName())
            )
        );
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

