package ru.geekbrains.usefullibraries.mvp.presenter;

import ru.geekbrains.usefullibraries.mvp.view.RepoRowView;

public interface RepoListPresenter {

    void bindRepoListRow(int pos, RepoRowView rowView);

    int getRepoCount();
}
