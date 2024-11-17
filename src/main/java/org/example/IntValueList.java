package org.example;

public interface IntValueList extends Iterable<IntBox> {

    int getInt(int index);

    int size();

    IntValueListStream intValueListStream();
}
