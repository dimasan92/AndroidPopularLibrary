package ru.geekbrains.usefullibraries;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import io.reactivex.Scheduler;

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
        getViewState().setButtonOneText(model.calculate(COUNTER_FOR_BUTTON_ONE));
    }

    void counterButtonTwoClick() {
        getViewState().setButtonTwoText(model.calculate(COUNTER_FOR_BUTTON_TWO));
    }

    void counterButtonThreeClick() {
        getViewState().setButtonThreeText(model.calculate(COUNTER_FOR_BUTTON_THREE));
    }
}
