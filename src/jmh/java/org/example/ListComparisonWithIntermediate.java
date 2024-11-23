package org.example;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.example.ListComparison.boxed;

@State(Scope.Benchmark)
public class ListComparisonWithIntermediate {

    private final int[] input = IntStream.range(0, 1_000_000)
            .map(i -> (int) (Math.random() * i))
            .toArray();

    @Benchmark
    public void array(Blackhole bh) {
        var res1 = new String[input.length];
        for (int i = 0; i < input.length; i++) {
            res1[i] = Integer.toString(input[i]);
        }

        var res2 = new String[input.length];
        for (int i = 0; i < input.length; i++) {
            var s = res1[i];
            res2[i] = s + s;
        }

        bh.consume(res2);
    }

    @Benchmark
    public void arrayList(Blackhole bh) {
        var input = Arrays.asList(boxed(this.input));

        var res1 = new ArrayList<String>(input.size());
        for (int i : input) {
            res1.add(Integer.toString(i));
        }

        var res2 = new ArrayList<String>(res1.size());
        for (String s : res1) {
            res2.add(s + s);
        }

        bh.consume(res2);
    }

    @Benchmark
    public void valueListFunctional(Blackhole bh) {
        var res = new IntValueArrayList(input)
                .mapInt(Integer::toString)
                .map(s -> s + s);

        bh.consume(res);
    }

    @Benchmark
    public void valueListProcedural(Blackhole bh) {
        var input = new IntValueArrayList(this.input);

        var res1Backing = new String[input.size()];
        for (int i = 0; i < input.size(); i++) {
            res1Backing[i] = Integer.toString(input.getInt(i));
        }
        var res1 = new ValueArrayList<>(res1Backing);

        var res2Backing = new String[res1.size()];
        for (int i = 0; i < res1.size(); i++) {
            var s = res1.get(i);
            res2Backing[i] = s + s;
        }
        var res2 = new ValueArrayList<>(res2Backing);

        bh.consume(res2);
    }
}
