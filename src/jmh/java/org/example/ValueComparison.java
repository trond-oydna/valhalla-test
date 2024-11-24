package org.example;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;

import java.util.stream.IntStream;

@State(Scope.Benchmark)
public class ValueComparison {

    @Benchmark
    public void value(Blackhole bh) {
        var input = new IntBox((int) (Math.random() * 1_000));
        var res = input.value() * 1_000;

        bh.consume(res);
    }

    @Benchmark
    public void ref(Blackhole bh) {
        Integer input = (int) (Math.random() * 1_000);
        Integer res = input * 1_000;

        bh.consume(res);
    }
}
