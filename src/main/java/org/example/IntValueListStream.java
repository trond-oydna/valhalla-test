package org.example;

import java.util.function.IntFunction;
import java.util.function.IntUnaryOperator;

public interface IntValueListStream {

    IntValueListStream map(IntUnaryOperator mapper);

    <R> ValueListStream<R> mapToObj(IntFunction<R> mapper);

    IntValueList toIntValueList();

    static value class Impl implements IntValueListStream {
        private final IntValueListStreamTraverser traverser;

        public Impl(IntValueListStreamTraverser traverser) {
            this.traverser = traverser;
        }

        @Override
        public IntValueListStream map(IntUnaryOperator mapper) {
            return new Impl(
                new IntValueListStreamTraverser.Mapper(
                    traverser,
                    mapper
                )
            );
        }

        @Override
        public <R> ValueListStream<R> mapToObj(IntFunction<R> mapper) {
            return new ValueListStream.Impl<>(
                new IntValueListStreamTraverser.ObjMapper<>(
                    traverser,
                    mapper
                )
            );
        }

        @Override
        public IntValueList toIntValueList() {
            int[] array = new int[traverser.size()];
            for (int i = 0; i < traverser.size(); i++) {
                array[i] = traverser.get(i);
            }
            return new IntValueArrayList(array);
        }
    }
}
