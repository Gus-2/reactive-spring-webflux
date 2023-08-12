package com.reactivespring.controller;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

public class SinksTest {

    @Test
    void sink() {
        Sinks.Many<Integer> replaySink = Sinks.many().replay().all();

        replaySink.emitNext(1, Sinks.EmitFailureHandler.FAIL_FAST);
        replaySink.emitNext(2, Sinks.EmitFailureHandler.FAIL_FAST);
        replaySink.emitNext(3, Sinks.EmitFailureHandler.FAIL_FAST);
        replaySink.emitNext(4, Sinks.EmitFailureHandler.FAIL_FAST);

        Flux<Integer> integerFlux = replaySink.asFlux();
        integerFlux.subscribe((i) -> {
            System.out.println("Subcribe : " + i);
        });

        Flux<Integer> integerFlux1 = replaySink.asFlux();
        integerFlux.subscribe((i) -> {
            System.out.println("Subcribe : " + i);
        });
    }

    @Test
    void sink_multicast() {
        Sinks.Many<Integer> multicast = Sinks.many().multicast().onBackpressureBuffer();

        multicast.emitNext(1, Sinks.EmitFailureHandler.FAIL_FAST);
        multicast.emitNext(2, Sinks.EmitFailureHandler.FAIL_FAST);
        multicast.emitNext(3, Sinks.EmitFailureHandler.FAIL_FAST);
        multicast.emitNext(4, Sinks.EmitFailureHandler.FAIL_FAST);

        Flux<Integer> integerFlux = multicast .asFlux();
        integerFlux.subscribe((i) -> {
            System.out.println("Subcribe : " + i);
        });

        // Only receive the new event published after the subscription
        Flux<Integer> integerFlux1 = multicast .asFlux();
        integerFlux.subscribe((i) -> {
            System.out.println("Subcribe : " + i);
        });

        multicast.emitNext(2, Sinks.EmitFailureHandler.FAIL_FAST);
    }

    @Test
    void sink_unicast() {

        // Unicast  => Only one subscriber
        Sinks.Many<Integer> multicast = Sinks.many().unicast().onBackpressureBuffer();

        multicast.emitNext(1, Sinks.EmitFailureHandler.FAIL_FAST);
        multicast.emitNext(2, Sinks.EmitFailureHandler.FAIL_FAST);
        multicast.emitNext(3, Sinks.EmitFailureHandler.FAIL_FAST);
        multicast.emitNext(4, Sinks.EmitFailureHandler.FAIL_FAST);

        Flux<Integer> integerFlux = multicast .asFlux();
        integerFlux.subscribe((i) -> {
            System.out.println("Subcribe : " + i);
        });

        // Only receive the new event published after the subscription
        Flux<Integer> integerFlux1 = multicast .asFlux();
        integerFlux.subscribe((i) -> {
            System.out.println("Subcribe : " + i);
        });

        multicast.emitNext(2, Sinks.EmitFailureHandler.FAIL_FAST);
    }
}
