package ru.geekbrains.usefullibraries.mvp.model.repo.cache;

import java.util.List;

import io.reactivex.Observable;
import ru.geekbrains.usefullibraries.mvp.model.entity.Repository;
import ru.geekbrains.usefullibraries.mvp.model.entity.User;

public interface IUserCache {

    void saveUser(User user, String username);

    Observable<User> getCachedUser(String username);

    void saveUserRepos(User user, List<Repository> repos);

    Observable<List<Repository>> getUserRepos(User user);
}
