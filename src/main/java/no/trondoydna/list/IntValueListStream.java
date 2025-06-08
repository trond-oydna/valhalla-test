package no.trondoydna.list;

import java.util.function.IntFunction;
import java.util.function.IntUnaryOperator;

public interface IntValueListStream {

    IntValueListStream map(IntUnaryOperator mapper);

    <R> ValueListStream<R> mapToObj(IntFunction<R> mapper);

    IntValueList toIntValueList();

    static IntValueListStream of(int... values) {
        return new IntValueListStream.Impl(
                new IntValueListStreamTraverser.IntArraySource(values)
        );
    }

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

    interface IntValueListStreamTraverser {

        int get(int index);

        int size();

        static value class IntValueListSource implements IntValueListStreamTraverser {
            private final IntValueList source;

            public IntValueListSource(IntValueList source) {
                this.source = source;
            }

            @Override
            public int get(int index) {
                return source.getInt(index);
            }

            @Override
            public int size() {
                return source.size();
            }
        }

        static value class IntArraySource implements IntValueListStreamTraverser {
            private final int[] source;

            public IntArraySource(int[] source) {
                this.source = source;
            }

            @Override
            public int get(int index) {
                return source[index];
            }

            @Override
            public int size() {
                return source.length;
            }
        }

        static value class Mapper implements IntValueListStreamTraverser {
            private final IntValueListStreamTraverser parent;
            private final IntUnaryOperator mapper;

            public Mapper(
                    IntValueListStreamTraverser parent,
                    IntUnaryOperator mapper
            ) {
                this.parent = parent;
                this.mapper = mapper;
            }

            @Override
            public int get(int index) {
                return mapper.applyAsInt(parent.get(index));
            }

            @Override
            public int size() {
                return parent.size();
            }
        }

        static value class ObjMapper<R> implements ValueListStream.ValueListStreamTraverser<R> {
            private final IntValueListStreamTraverser parent;
            private final IntFunction<R> mapper;

            public ObjMapper(
                    IntValueListStreamTraverser parent,
                    IntFunction<R> mapper
            ) {
                this.parent = parent;
                this.mapper = mapper;
            }

            @Override
            public R get(int index) {
                return mapper.apply(parent.get(index));
            }

            @Override
            public int size() {
                return parent.size();
            }
        }
    }
}
