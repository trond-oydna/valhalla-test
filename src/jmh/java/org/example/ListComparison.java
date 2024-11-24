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
import java.util.stream.StreamSupport;

@State(Scope.Benchmark)
public class ListComparison {

    private final int[] input = IntStream.range(0, 1_000_000)
            .map(i -> (int) (Math.random() * i))
            .toArray();

    @Benchmark
    public void array(Blackhole bh) {
        var res = new String[input.length];
        for (int i = 0; i < input.length; i++) {
            var s = Integer.toString(input[i]);
            res[i] = s + s;
        }

        bh.consume(res);
    }

    @Benchmark
    public void arrayList(Blackhole bh) {
        var input = Arrays.asList(boxed(this.input));

        var res = new ArrayList<String>(input.size());
        for (Integer i : input) {
            var s = Integer.toString(i);
            res.add(s + s);
        }

        bh.consume(res);
    }

    @Benchmark
    public void valueList(Blackhole bh) {
        var input = new IntValueArrayList(this.input);

        var resBacking = new String[input.size()];
        for (int i = 0; i < input.size(); i++) {
            var s = Integer.toString(input.getInt(i));
            resBacking[i] = s + s;
        }
        var res = new ValueArrayList<>(resBacking);

        bh.consume(res);
    }

    @Benchmark
    public void valueListStream(Blackhole bh) {
        var res = new IntValueArrayList(input)
                .valueListStream()
                .map(IntBox::toString)
                .map(s -> s + s)
                .toValueArrayList(String[]::new);

        bh.consume(res);
    }

    @Benchmark
    public void intValueListStream(Blackhole bh) {
        var res = new IntValueArrayList(input)
                .intValueListStream()
                .mapToObj(Integer::toString)
                .map(s -> s + s)
                .toValueArrayList(String[]::new);

        bh.consume(res);
    }

    @Benchmark
    public void intStream(Blackhole bh) {
        var res = IntStream.of(input)
                .mapToObj(Integer::toString)
                .map(s -> s + s)
                .toArray(String[]::new);

        bh.consume(res);
    }

    @Benchmark
    public void objStream(Blackhole bh) {
        var res = Stream.of(boxed(input))
                .map(Objects::toString)
                .map(s -> s + s)
                .toArray(String[]::new);

        bh.consume(res);
    }

    @Benchmark
    public void objIntBoxStream(Blackhole bh) {
        var res = Stream.of(intBoxed(input))
                .map(Objects::toString)
                .map(s -> s + s)
                .toArray(String[]::new);

        bh.consume(res);
    }

    static Integer[] boxed(int[] input) {
        Integer[] res = new Integer[input.length];
        for (int i = 0; i < res.length; i++) {
            res[i] = input[i];
        }
        return res;
    }

    static IntBox[] intBoxed(int[] input) {
        IntBox[] res = new IntBox[input.length];
        for (int i = 0; i < res.length; i++) {
            res[i] = new IntBox(input[i]);
        }
        return res;
    }
}
