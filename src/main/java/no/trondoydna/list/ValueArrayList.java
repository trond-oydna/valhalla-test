package no.trondoydna.list;

import java.util.Arrays;
import java.util.Iterator;

public value class ValueArrayList<T> implements ValueList<T> {
    private T[] elements;

    ValueArrayList(T[] elements) {
        this.elements = elements;
    }

    @Override
    public T get(int index) {
        return elements[index];
    }

    @Override
    public int size() {
        return elements.length;
    }

    @Override
    public ValueListStream<T> valueListStream() {
        return new ValueListStream.Impl<>(
            new ValueListStream.ValueListStreamTraverser.ValueListSource<>(this)
        );
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayIterator<>(elements);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(elements);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ValueArrayList) {
            return Arrays.equals(elements, ((ValueArrayList<?>) obj).elements);
        }
        return false;
    }

    @Override
    public String toString() {
        return Arrays.toString(elements);
    }

    private static class ArrayIterator<T> implements Iterator<T> {
        private final T[] elements;
        private int index = 0;

        ArrayIterator(T[] elements) {
            this.elements = elements;
        }

        @Override
        public boolean hasNext() {
            return index < elements.length;
        }

        @Override
        public T next() {
            T next = elements[index];
            index++;
            return next;
        }
    }
}
