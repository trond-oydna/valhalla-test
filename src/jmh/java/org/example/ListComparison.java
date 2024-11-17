package org.example;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;

import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@State(Scope.Benchmark)
public class ListComparison {

    @Benchmark
    public void arrayNoIntermediate(Blackhole bh) {
        var input = new int[]{0, 1, 2, 3};

        var res = new String[4];
        for (int i = 0; i < 4; i++) {
            var s = Integer.toString(input[i]);
            res[i] = s + s;
        }

        bh.consume(res);
    }

    @Benchmark
    public void arrayStoringIntermediate(Blackhole bh) {
        var input = new int[]{0, 1, 2, 3};

        var res1 = new String[4];
        for (int i = 0; i < 4; i++) {
            res1[i] = Integer.toString(input[i]);
        }

        var res2 = new String[4];
        for (int i = 0; i < 4; i++) {
            var s = res1[i];
            res2[i] = s + s;
        }

        bh.consume(res2);
    }

    @Benchmark
    public void arrayListNoIntermediate(Blackhole bh) {
        var input = new int[]{0, 1, 2, 3};

        var res = new ArrayList<String>(4);
        for (int i : input) {
            var s = Integer.toString(i);
            res.add(s + s);
        }

        bh.consume(res);
    }

    @Benchmark
    public void arrayListStoringIntermediate(Blackhole bh) {
        var input = new int[]{0, 1, 2, 3};

        var res1 = new ArrayList<String>(4);
        for (int i : input) {
            res1.add(Integer.toString(i));
        }

        var res2 = new ArrayList<String>(4);
        for (String s : res1) {
            res2.add(s + s);
        }

        bh.consume(res2);
    }

    @Benchmark
    public void valueListFunctional(Blackhole bh) {
        var res = new IntValueArrayList(new int[]{0, 1, 2, 3})
                .mapInt(Integer::toString)
                .map(s -> s + s);

        bh.consume(res);
    }

    @Benchmark
    public void valueListProceduralNoIntermediate(Blackhole bh) {
        var input = new IntValueArrayList(new int[]{0, 1, 2, 3});

        var resBacking = new String[4];
        for (int i = 0; i < 4; i++) {
            var s = Integer.toString(input.getInt(i));
            resBacking[i] = s + s;
        }
        var res = new ValueArrayList<>(resBacking);

        bh.consume(res);
    }

    @Benchmark
    public void valueListProceduralStoringIntermediate(Blackhole bh) {
        var input = new IntValueArrayList(new int[]{0, 1, 2, 3});

        var res1Backing = new String[4];
        for (int i = 0; i < 4; i++) {
            res1Backing[i] = Integer.toString(input.getInt(i));
        }
        var res1 = new ValueArrayList<>(res1Backing);

        var res2Backing = new String[4];
        for (int i = 0; i < 4; i++) {
            var s = res1.get(i);
            res2Backing[i] = s + s;
        }
        var res2 = new ValueArrayList<>(res2Backing);

        bh.consume(res2);
    }

    @Benchmark
    public void valueListStream(Blackhole bh) {
        var res = new IntValueArrayList(new int[]{0, 1, 2, 3})
                .valueListStream()
                .map(IntBox::toString)
                .map(s -> s + s)
                .toValueArrayList(String[]::new);

        bh.consume(res);
    }

    @Benchmark
    public void intValueListStream(Blackhole bh) {
        var res = new IntValueArrayList(new int[]{0, 1, 2, 3})
                .intValueListStream()
                .mapToObj(Integer::toString)
                .map(s -> s + s)
                .toValueArrayList(String[]::new);

        bh.consume(res);
    }

    @Benchmark
    public void intStream(Blackhole bh) {
        var res = IntStream.of(0, 1, 2, 3)
                .mapToObj(Integer::toString)
                .map(s -> s + s)
                .toArray(String[]::new);

        bh.consume(res);
    }


    @Benchmark
    public void objStream(Blackhole bh) {
        var res = Stream.of(0, 1, 2, 3)
                .map(Objects::toString)
                .map(s -> s + s)
                .toArray(String[]::new);

        bh.consume(res);
    }
}
