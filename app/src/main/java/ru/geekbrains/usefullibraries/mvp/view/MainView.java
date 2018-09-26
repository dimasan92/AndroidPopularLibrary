package ru.geekbrains.usefullibraries.mvp.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import ru.geekbrains.usefullibraries.mvp.presenter.IReposListPresenter;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface MainView extends MvpView {

    void initList(final IReposListPresenter reposListPresenter);

    void setUsernameText(final String username);

    void loadImage(final String url);
}
