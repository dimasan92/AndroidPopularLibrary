package ru.geekbrains.usefullibraries.mvp.model.repo;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import ru.geekbrains.usefullibraries.mvp.model.NetworkStatus;
import ru.geekbrains.usefullibraries.mvp.model.api.ApiService;
import ru.geekbrains.usefullibraries.mvp.model.api.UserRepo;
import ru.geekbrains.usefullibraries.mvp.model.entity.Repository;
import ru.geekbrains.usefullibraries.mvp.model.entity.User;
import ru.geekbrains.usefullibraries.mvp.model.repo.usercache.RealmUserCache;
import ru.geekbrains.usefullibraries.mvp.model.repo.usercache.UserCache;

@Singleton
public final class UserRepoImpl implements UserRepo {

    private final UserCache userCache;
    private final ApiService apiService;

    @Inject
    UserRepoImpl(@Named("realm_user_cache") UserCache userCache, ApiService apiService) {
        this.apiService = apiService;
        this.userCache = userCache;
    }

    @Override
    public Single<User> getUser(String username) {
        if (NetworkStatus.isOnline()) {
            return apiService.getUser(username)
                    .subscribeOn(Schedulers.io())
                    .map(user -> {
                        userCache.saveUser(user);
                        return user;
                    });
        } else {
            return userCache.getCachedUser(username);
        }
    }

    @Override
    public Single<List<Repository>> getUserRepos(User user) {
        if (NetworkStatus.isOnline()) {
            return apiService.getUserRepos(user.getReposUrl())
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
