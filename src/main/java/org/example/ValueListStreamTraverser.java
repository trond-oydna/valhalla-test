package org.example;

import java.util.function.Function;

public interface ValueListStreamTraverser<T> {

    T get(int index);

    int size();

    static value class Source<T> implements ValueListStreamTraverser<T> {
        private final ValueList<T> source;

        public Source(ValueList<T> source) {
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
