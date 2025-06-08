package no.trondoydna;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;

@State(Scope.Benchmark)
public class PairComparison {

    @Benchmark
    public void value(Blackhole bh) {
        var first = (int) (Math.random() * 1_000);
        var second = (int) (Math.random() * 1_000);

        var input = new IntValuePair(first, second);
        bh.consume(input);

        var res = input.first() + input.second();
        bh.consume(res);
    }

    @Benchmark
    public void ref(Blackhole bh) {
        var first = (int) (Math.random() * 1_000);
        var second = (int) (Math.random() * 1_000);

        var input = new IntRefPair(first, second);
        bh.consume(input);

        var res = input.first() + input.second();
        bh.consume(res);
    }
}
