package ru.geekbrains.usefullibraries.mvp.model.repo;

import java.util.List;

import io.reactivex.Observable;
import ru.geekbrains.usefullibraries.mvp.model.api.ApiHolder;
import ru.geekbrains.usefullibraries.mvp.model.entity.Repo;
import ru.geekbrains.usefullibraries.mvp.model.entity.User;

public final class UsersRepo {

    public Observable<User> getUser(final String username) {
        return ApiHolder.getApi().getUser(username);
    }

    public Observable<List<Repo>> getUserRepos(final String url) {
        return ApiHolder.getApi().getUserRepos(url);
    }
}
