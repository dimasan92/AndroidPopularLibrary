package ru.geekbrains.usefullibraries.mvp.model.api;

import java.util.List;

import io.reactivex.Single;
import ru.geekbrains.usefullibraries.mvp.model.entity.Repository;
import ru.geekbrains.usefullibraries.mvp.model.entity.User;

public interface UserRepo {

    Single<User> getUser(String username);

    Single<List<Repository>> getUserRepos(User user);
}
