package ru.geekbrains.usefullibraries.mvp.view;

public interface ReposListView {

    interface RepoRowView {

        void setRepoName(final String name);
    }

    void refreshReposList();
}
