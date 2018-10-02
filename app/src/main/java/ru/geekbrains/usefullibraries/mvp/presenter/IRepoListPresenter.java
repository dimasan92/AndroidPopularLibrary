package ru.geekbrains.usefullibraries.mvp.presenter;

import ru.geekbrains.usefullibraries.mvp.view.RepoRowView;

public interface IRepoListPresenter {

    void bindRepoListRow(int pos, RepoRowView rowView);

    int getRepoCount();
}
