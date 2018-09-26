package ru.geekbrains.usefullibraries.mvp.presenter;

import android.annotation.SuppressLint;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
import ru.geekbrains.usefullibraries.mvp.model.repo.UsersRepo;
import ru.geekbrains.usefullibraries.mvp.view.MainView;
import timber.log.Timber;

@InjectViewState
public final class MainPresenter extends MvpPresenter<MainView> {

    private final Scheduler uiScheduler;
    private final UsersRepo usersRepo;

    public MainPresenter(Scheduler uiScheduler) {
        this.uiScheduler = uiScheduler;
        usersRepo = new UsersRepo();
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        loadData();
    }

    @SuppressLint("CheckResult")
    private void loadData() {
        usersRepo.getUser("googlesamples")
                .subscribeOn(Schedulers.io())
                .observeOn(uiScheduler)
                .subscribe(user -> {
                    getViewState().setUsernameText(user.getLogin());
                    getViewState().loadImage(user.getAvatarUrl());
                }, throwable -> Timber.e(throwable, "Failed to get user"));
    }
}
