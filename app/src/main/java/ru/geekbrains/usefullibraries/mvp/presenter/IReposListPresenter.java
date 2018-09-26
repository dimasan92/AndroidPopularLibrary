package ru.geekbrains.usefullibraries.mvp.presenter;

import java.util.List;

import ru.geekbrains.usefullibraries.mvp.model.entity.Repo;
import ru.geekbrains.usefullibraries.mvp.view.ReposListView;

public interface IReposListPresenter {

    void setRepos(final List<Repo> repos);

    void attachView(final ReposListView view);

    void bindViewAt(int position, ReposListView.RepoRowView view);

    int getReposCount();
}
