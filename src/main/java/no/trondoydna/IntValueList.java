package no.trondoydna;

public interface IntValueList extends Iterable<Integer> {

    int getInt(int index);

    int size();

    IntValueListStream intValueListStream();
}
