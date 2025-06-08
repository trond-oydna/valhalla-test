package no.trondoydna;

import java.util.function.Function;

public interface ValueList<T> extends Iterable<T> {

    T get(int index);

    int size();

    ValueListStream<T> valueListStream();

    default <R> ValueList<R> map(Function<? super T, ? extends R> mapper) {
        @SuppressWarnings("unchecked")
        R[] a = (R[]) new Object[size()];
        for (int i = 0; i < size(); i++) {
            a[i] = mapper.apply(get(i));
        }
        return new ValueArrayList<>(a);
    }
}
