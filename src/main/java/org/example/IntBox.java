package org.example;

public value record IntBox(int value) {
    @Override
    public String toString() {
        return Integer.toString(value);
    }
}
