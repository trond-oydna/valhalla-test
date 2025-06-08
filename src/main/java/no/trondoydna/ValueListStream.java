package no.trondoydna;

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
}
