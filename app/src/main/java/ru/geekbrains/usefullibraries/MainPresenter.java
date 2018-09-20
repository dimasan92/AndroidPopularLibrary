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

    private static final int COUNTER_FOR_BUTTON_ONE = 0;
    private static final int COUNTER_FOR_BUTTON_TWO = 1;
    private static final int COUNTER_FOR_BUTTON_THREE = 2;

    private final CounterModel model;
    private final Scheduler uiScheduler;

    private final CompositeDisposable disposables;

    MainPresenter(Scheduler uiScheduler) {
        this.model = new CounterModel();
        this.uiScheduler = uiScheduler;
        disposables = new CompositeDisposable();
    }

    void resume(Observable<CharSequence> textViewObservable) {
        startObserveTextView(textViewObservable);
    }

    void pause() {
        disposables.dispose();
    }

    void counterButtonOneClick() {
        counterButtonClick(COUNTER_FOR_BUTTON_ONE, text -> getViewState().setButtonOneText(text));
    }

    void counterButtonTwoClick() {
        counterButtonClick(COUNTER_FOR_BUTTON_TWO, text -> getViewState().setButtonTwoText(text));
    }

    void counterButtonThreeClick() {
        counterButtonClick(COUNTER_FOR_BUTTON_THREE, text -> getViewState().setButtonThreeText(text));
    }

    private void startObserveTextView(Observable<CharSequence> textViewObservable) {
        disposables.add(textViewObservable.subscribe((text) -> getViewState().setTextViewText(text),
                Throwable::printStackTrace));
    }

    private void counterButtonClick(int counterForButton, Consumer<Integer> buttonTextSetter) {
        Disposable disposable = model.calculate(counterForButton)
                .observeOn(uiScheduler)
                .subscribe(buttonTextSetter, Throwable::printStackTrace);
    }
}
