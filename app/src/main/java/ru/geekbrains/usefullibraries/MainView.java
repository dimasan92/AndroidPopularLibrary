package ru.geekbrains.usefullibraries;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface MainView extends MvpView {

    void setButtonOneText(int val);

    void setButtonTwoText(int val);

    void setButtonThreeText(int val);

    void setTextViewText(CharSequence sequence);
}
