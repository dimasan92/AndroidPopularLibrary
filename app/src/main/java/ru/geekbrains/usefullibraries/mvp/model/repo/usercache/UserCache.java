package ru.geekbrains.usefullibraries.mvp.model.repo.usercache;

import java.util.List;

import io.reactivex.Single;
import ru.geekbrains.usefullibraries.mvp.model.entity.Repository;
import ru.geekbrains.usefullibraries.mvp.model.entity.User;

public interface UserCache {

    void saveUser(User user);

    Single<User> getCachedUser(String username);

    void saveUserRepos(User user, List<Repository> repos);

    Single<List<Repository>> getUserRepos(User user);
}
