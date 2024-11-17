package org.example;

import java.util.Arrays;
import java.util.Iterator;
import java.util.function.IntFunction;

public value class IntValueArrayList implements IntValueList, ValueList<IntBox> {
    private int[] elements;

    public IntValueArrayList(int[] elements) {
        this.elements = elements;
    }

    @Override
    public IntBox get(int index) {
        return new IntBox(getInt(index));
    }

    @Override
    public int getInt(int index) {
        return elements[index];
    }

    @Override
    public int size() {
        return elements.length;
    }

    @Override
    public ValueListStream<IntBox> valueListStream() {
        return new ValueListStream.Impl<>(
            new ValueListStreamTraverser.Source<>(this)
        );
    }

    @Override
    public IntValueListStream intValueListStream() {
        return new IntValueListStream.Impl(
            new IntValueListStreamTraverser.Source(this)
        );
    }

    @Override
    public Iterator<IntBox> iterator() {
        return new IntArrayIterator(elements);
    }

    public <R> ValueList<R> mapInt(IntFunction<? extends R> mapper) {
        @SuppressWarnings("unchecked")
        R[] a = (R[]) new Object[size()];
        for (int i = 0; i < size(); i++) {
            a[i] = mapper.apply(getInt(i));
        }
        return new ValueArrayList<>(a);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(elements);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof IntValueArrayList) {
            return Arrays.equals(elements, ((IntValueArrayList) obj).elements);
        }
        return false;
    }

    @Override
    public String toString() {
        return Arrays.toString(elements);
    }

    private class IntArrayIterator implements Iterator<IntBox> {
        private final int[] elements;
        private int index = 0;

        public IntArrayIterator(int[] elements) {
            this.elements = elements;
        }

        @Override
        public boolean hasNext() {
            return index < elements.length;
        }

        @Override
        public IntBox next() {
            int next = elements[index];
            index++;
            return new IntBox(next);
        }
    }
}
