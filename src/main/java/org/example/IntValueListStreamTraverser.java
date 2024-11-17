package org.example;

import java.util.function.IntFunction;
import java.util.function.IntUnaryOperator;

public interface IntValueListStreamTraverser {

    int get(int index);

    int size();

    static value class Source implements IntValueListStreamTraverser {
        private final IntValueList source;

        public Source(IntValueList source) {
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

    static value class ObjMapper<R> implements ValueListStreamTraverser<R> {
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
