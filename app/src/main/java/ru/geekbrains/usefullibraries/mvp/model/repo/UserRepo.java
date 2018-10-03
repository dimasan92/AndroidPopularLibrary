package ru.geekbrains.usefullibraries.mvp.model.repo;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import ru.geekbrains.usefullibraries.mvp.model.NetworkStatus;
import ru.geekbrains.usefullibraries.mvp.model.api.ApiHolder;
import ru.geekbrains.usefullibraries.mvp.model.entity.Repository;
import ru.geekbrains.usefullibraries.mvp.model.entity.User;
import ru.geekbrains.usefullibraries.mvp.model.repo.cache.IUserCache;
import ru.geekbrains.usefullibraries.mvp.model.repo.cache.RealmUserCache;

public final class UserRepo {

    private final IUserCache userCache;

    public UserRepo() {
        this.userCache = new RealmUserCache();
    }

    public Observable<User> getUser(String username) {
        if (NetworkStatus.isOnline()) {
            return ApiHolder.getInstance().getApi().getUser(username)
                    .subscribeOn(Schedulers.io())
                    .map(user -> {
                        userCache.saveUser(user, username);
                        return user;
                    });
        } else {
            return userCache.getCachedUser(username);
        }
    }

    public Observable<List<Repository>> getUserRepos(User user) {
        if (NetworkStatus.isOnline()) {
            return ApiHolder.getInstance().getApi().getUserRepos(user.getReposUrl())
                    .subscribeOn(Schedulers.io())
                    .map(repos -> {
                        userCache.saveUserRepos(user, repos);
                        return repos;
                    });
        } else {
            return userCache.getUserRepos(user);
        }
    }
}
