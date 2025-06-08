package no.trondoydna.list;

import java.util.function.Function;
import java.util.function.IntFunction;

public interface ValueListStream<T> {

    <R> ValueListStream<R> map(Function<? super T, ? extends R> mapper);

    ValueArrayList<T> toValueArrayList(IntFunction<T[]> toArray);

    @SafeVarargs
    static <T> ValueListStream<T> of(T... values) {
        return new ValueListStream.Impl<>(
                new ValueListStreamTraverser.ArraySource<>(values)
        );
    }

    static value class Impl<T> implements ValueListStream<T> {
        private final ValueListStreamTraverser<T> traverser;

        public Impl(ValueListStreamTraverser<T> traverser) {
            this.traverser = traverser;
        }

        @Override
        public <R> ValueListStream<R> map(Function<? super T, ? extends R> mapper) {
            return new Impl<R>(
                new ValueListStreamTraverser.Mapper<>(
                    traverser,
                    mapper
                )
            );
        }

        @Override
        public ValueArrayList<T> toValueArrayList(IntFunction<T[]> toArray) {
            T[] array = toArray.apply(traverser.size());
            for (int i = 0; i < traverser.size(); i++) {
                array[i] = traverser.get(i);
            }
            return new ValueArrayList<>(array);
        }
    }

    interface ValueListStreamTraverser<T> {

        T get(int index);
    
        int size();

        static value class ValueListSource<T> implements ValueListStreamTraverser<T> {
            private final ValueList<T> source;

            public ValueListSource(ValueList<T> source) {
                this.source = source;
            }

            @Override
            public T get(int index) {
                return source.get(index);
            }

            @Override
            public int size() {
                return source.size();
            }
        }

        static value class ArraySource<T> implements ValueListStreamTraverser<T> {
            private final T[] source;

            public ArraySource(T[] source) {
                this.source = source;
            }

            @Override
            public T get(int index) {
                return source[index];
            }

            @Override
            public int size() {
                return source.length;
            }
        }

        static value class Mapper<T, R> implements ValueListStreamTraverser<R> {
            private final ValueListStreamTraverser<T> parent;
            private final Function<? super T, ? extends R> mapper;

            public Mapper(
                ValueListStreamTraverser<T> parent,
                Function<? super T, ? extends R> mapper
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
