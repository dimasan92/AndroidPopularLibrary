package ru.geekbrains.usefullibraries.mvp.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface MainView extends MvpView {

    void showLoading();

    void hideLoading();

    void showAvatar(String avatarUrl);

    void setUsername(String username);

    void updateRepoList();

    void showError(String message);
}
