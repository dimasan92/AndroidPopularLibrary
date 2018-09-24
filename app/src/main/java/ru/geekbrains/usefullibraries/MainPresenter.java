package ru.geekbrains.usefullibraries;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

@InjectViewState
public class MainPresenter extends MvpPresenter<MainView> {

    private final CounterModel model;
    private final Scheduler uiScheduler;

    private final CompositeDisposable disposables;

    MainPresenter(Scheduler uiScheduler) {
        this.model = new CounterModel();
        this.uiScheduler = uiScheduler;
        disposables = new CompositeDisposable();
    }

    void resume() {
    }

    void pause() {

    }
}
