package no.trondoydna.list;

import no.trondoydna.pair.IntRefPair;
import no.trondoydna.pair.IntValuePair;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;

import java.util.stream.IntStream;

@State(Scope.Benchmark)
public class ArrayComparison {

    private final int[] input = IntStream.range(0, 1_000_000)
            .map(i -> (int) (Math.random() * i))
            .toArray();

    @Benchmark
    public void valuePair(Blackhole bh) {
        var res = new IntValuePair[input.length];
        for (int i = 0; i < input.length; i++) {
            res[i] = new IntValuePair(i, i * 2);
        }

        bh.consume(res);
    }

    @Benchmark
    public void refPair(Blackhole bh) {
        var res = new IntRefPair[input.length];
        for (int i = 0; i < input.length; i++) {
            res[i] = new IntRefPair(i, i * 2);
        }

        bh.consume(res);
    }
}
