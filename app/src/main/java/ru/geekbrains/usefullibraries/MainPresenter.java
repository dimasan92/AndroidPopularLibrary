package ru.geekbrains.usefullibraries;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

@InjectViewState
public class MainPresenter extends MvpPresenter<MainView> {

    private static final int COUNTER_FOR_BUTTON_ONE = 0;
    private static final int COUNTER_FOR_BUTTON_TWO = 1;
    private static final int COUNTER_FOR_BUTTON_THREE = 2;

    private final CounterModel model;
    private final Scheduler uiScheduler;

    MainPresenter(Scheduler uiScheduler) {
        this.model = new CounterModel();
        this.uiScheduler = uiScheduler;
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

    private void counterButtonClick(int counterForButton, Consumer<Integer> buttonTextSetter) {
        Disposable disposable = model.calculate(counterForButton)
                .observeOn(uiScheduler)
                .subscribe(buttonTextSetter, Throwable::printStackTrace);
    }
}
