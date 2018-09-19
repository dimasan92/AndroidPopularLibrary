package ru.geekbrains.usefullibraries;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

class CounterModel {

    private static final int ADDITION = 1;

    private List<Integer> counters;

    CounterModel() {
        counters = new ArrayList<>();
        counters.add(0);
        counters.add(0);
        counters.add(0);
    }

    Single<Integer> calculate(int index) {
        return Single.fromCallable(() -> counters.set(index, counters.get(index) + ADDITION))
                .subscribeOn(Schedulers.computation())
                .map(integer -> integer + ADDITION)
                .observeOn(Schedulers.io());
    }
}
