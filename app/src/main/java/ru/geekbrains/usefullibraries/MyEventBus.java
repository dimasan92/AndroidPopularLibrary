package ru.geekbrains.usefullibraries;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

public final class MyEventBus<T> {

    private final Subject<T> bus;

    public MyEventBus(Observable<T> sourceOne, Observable<T> sourceTwo) {
        bus = PublishSubject.create();
        sourceOne.subscribe(bus);
        sourceTwo.subscribe(bus);
    }

    public void sendMessage(T message) {
        bus.onNext(message);
    }

    public void sunscribe(Observer<T> observerOne, Observer<T> observerTwo) {
        bus.subscribe(observerOne);
        bus.subscribe(observerTwo);
    }
}
