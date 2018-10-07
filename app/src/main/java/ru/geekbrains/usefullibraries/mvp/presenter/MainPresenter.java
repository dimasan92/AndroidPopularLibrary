package ru.geekbrains.usefullibraries.mvp.presenter;

import android.annotation.SuppressLint;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import ru.geekbrains.usefullibraries.mvp.model.api.UserRepo;
import ru.geekbrains.usefullibraries.mvp.model.entity.User;
import ru.geekbrains.usefullibraries.mvp.model.repo.UserRepoImpl;
import ru.geekbrains.usefullibraries.mvp.view.MainView;
import ru.geekbrains.usefullibraries.mvp.view.RepoRowView;
import timber.log.Timber;

@InjectViewState
public final class MainPresenter extends MvpPresenter<MainView> {

    private static final String GITHUB_USER_GOOGLE = "googlesamples";

    @Inject
    UserRepo userRepo;

    private final RepoListPresenter repoListPresenter;
    private final Scheduler uiScheduler;
    private User user;

    public MainPresenter(Scheduler uiScheduler) {
        this.uiScheduler = uiScheduler;
        repoListPresenter = new RepoListPresenterImpl();
    }

    public void onPermissionsGranted() {
        loadInfo();
    }

    public RepoListPresenter getRepoListPresenter() {
        return repoListPresenter;
    }

    @SuppressLint("CheckResult")
    private void loadInfo() {
        getViewState().showLoading();
        userRepo.getUser(GITHUB_USER_GOOGLE)
                .observeOn(uiScheduler)
                .subscribe(user -> {
                    this.user = user;
                    getViewState().setUsername(user.getLogin());
                    getViewState().showAvatar(user.getAvatarUrl());
                    userRepo.getUserRepos(user)
                            .observeOn(uiScheduler)
                            .subscribe(repositories -> {
                                getViewState().hideLoading();
                                this.user.setRepos(repositories);
                                getViewState().updateRepoList();
                            }, throwable -> {
                                Timber.e(throwable, "Failed to get user repos");
                                getViewState().hideLoading();
                                getViewState().showError(throwable.getMessage());
                            });
                }, throwable -> {
                    getViewState().hideLoading();
                    Timber.e(throwable, "Failed to get user");
                    getViewState().showError(throwable.getMessage());
                });
    }

    class RepoListPresenterImpl implements RepoListPresenter {

        @Override
        public void bindRepoListRow(int pos, RepoRowView rowView) {
            if (user != null) {
                rowView.setTitle(user.getRepos().get(pos).getName());
            }
        }

        @Override
        public int getRepoCount() {
            return user == null || user.getRepos() == null ? 0 : user.getRepos().size();
        }
    }
}
