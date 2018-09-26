package ru.geekbrains.usefullibraries.mvp.presenter;

import android.annotation.SuppressLint;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.Collections;
import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
import ru.geekbrains.usefullibraries.mvp.model.entity.Repo;
import ru.geekbrains.usefullibraries.mvp.model.entity.User;
import ru.geekbrains.usefullibraries.mvp.model.repo.UsersRepo;
import ru.geekbrains.usefullibraries.mvp.view.MainView;
import ru.geekbrains.usefullibraries.mvp.view.ReposListView;
import timber.log.Timber;

@InjectViewState
public final class MainPresenter extends MvpPresenter<MainView> {

    private static final String GOOGLE_USERNAME = "googlesamples";

    private final Scheduler uiScheduler;
    private final UsersRepo usersRepo;
    private final IReposListPresenter reposListPresenter;

    private boolean isReposDownloaded = false;

    public MainPresenter(Scheduler uiScheduler) {
        this.uiScheduler = uiScheduler;
        usersRepo = new UsersRepo();
        reposListPresenter = new ReposListPresenter();
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getViewState().initList(reposListPresenter);
    }

    public void viewIsReady() {
        if (!isReposDownloaded) {
            loadData();
            isReposDownloaded = true;
        }
    }

    @SuppressLint("CheckResult")
    private void loadData() {
        usersRepo.getUser(GOOGLE_USERNAME)
                .subscribeOn(Schedulers.io())
                .observeOn(uiScheduler)
                .doOnNext(this::getUserRepos)
                .subscribe(user -> {
                    getViewState().setUsernameText(user.getLogin());
                    getViewState().loadImage(user.getAvatarUrl());
                }, throwable -> Timber.e(throwable, "Failed to get user"));
    }

    @SuppressLint("CheckResult")
    private void getUserRepos(final User user) {
        usersRepo.getUserRepos(user.getReposUrl())
                .subscribeOn(Schedulers.io())
                .observeOn(uiScheduler)
                .subscribe(reposListPresenter::setRepos,
                        throwable -> Timber.e(throwable, "Failed to get repos")
                );
    }

    private final class ReposListPresenter implements IReposListPresenter {

        private ReposListView view;
        private List<Repo> repos = Collections.emptyList();

        @Override
        public void setRepos(final List<Repo> repos) {
            this.repos = repos;
            view.refreshReposList();
        }

        @Override
        public void attachView(final ReposListView view) {
            this.view = view;
        }

        @Override
        public void bindViewAt(final int position, final ReposListView.RepoRowView view) {
            Repo repo = repos.get(position);
            view.setRepoName(repo.getName());
        }

        @Override
        public int getReposCount() {
            return repos.size();
        }
    }
}
