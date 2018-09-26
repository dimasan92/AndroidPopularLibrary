package ru.geekbrains.usefullibraries.mvp.model.repo;

import io.reactivex.Observable;
import ru.geekbrains.usefullibraries.mvp.model.api.ApiHolder;
import ru.geekbrains.usefullibraries.mvp.model.entity.User;

public final class UsersRepo {

    public Observable<User> getUser(String username) {
        return ApiHolder.getApi().getUser(username);
    }
}
