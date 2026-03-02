package no.trondoydna;

import no.trondoydna.list.IntValueArrayList;
import no.trondoydna.list.ValueArrayList;

public class Main {
    public static void main(String[] args) {
        var res = new IntValueArrayList(new int[]{0, 1, 2, 3})
                .intValueListStream()
                .mapToObj(Integer::toString)
                .map(s -> s + s)
                .toValueArrayList(String[]::new);

        System.out.println(res);

        System.out.println("Integer.class.isValue: " + Integer.class.isValue());
        System.out.println("ValueArrayList.class.isValue: " + ValueArrayList.class.isValue());
    }
}
