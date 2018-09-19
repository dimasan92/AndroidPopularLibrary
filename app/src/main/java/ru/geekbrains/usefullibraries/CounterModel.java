package ru.geekbrains.usefullibraries;

import java.util.ArrayList;
import java.util.List;

class CounterModel {

    private List<Integer> counters;

    CounterModel() {
        counters = new ArrayList<>();
        counters.add(0);
        counters.add(0);
        counters.add(0);
    }

    Integer calculate(int index) {
        counters.set(index, counters.get(index) + 1);
        return counters.get(index);
    }
}
