package org.example;

public class Main {
    public static void main(String[] args) {
        var res = new IntValueArrayList(new int[]{0, 1, 2, 3})
                .intValueListStream()
                .mapToObj(Integer::toString)
                .map(s -> s + s)
                .toValueArrayList(String[]::new);

        System.out.println(res);
    }
}
