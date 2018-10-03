package ru.geekbrains.usefullibraries.mvp.model.repo.cache;

import java.util.List;

import io.paperdb.Paper;
import io.reactivex.Observable;
import ru.geekbrains.usefullibraries.Utils;
import ru.geekbrains.usefullibraries.mvp.model.entity.Repository;
import ru.geekbrains.usefullibraries.mvp.model.entity.User;

public final class PaperUserCache implements IUserCache {

    @Override
    public void saveUser(User user, String username) {
        Paper.book("users").write(username, user);
    }

    @Override
    public Observable<User> getCachedUser(String username) {
        if (!Paper.book("users").contains(username)) {
            return Observable.error(new RuntimeException("No such user in cache"));
        }
        return Observable.fromCallable(() -> Paper.book("users").read(username));
    }

    @Override
    public void saveUserRepos(User user, List<Repository> repos) {
        String sha1 = Utils.SHA1(user.getReposUrl());
        Paper.book("repos").write(sha1, repos);
    }

    @Override
    public Observable<List<Repository>> getUserRepos(User user) {
        String sha1 = Utils.SHA1(user.getReposUrl());
        if (!Paper.book("repos").contains(sha1)) {
            return Observable.error(new RuntimeException("No repos for such user in cache"));
        }
        return Observable.fromCallable(() -> Paper.book("repos").read(sha1));
    }
}
