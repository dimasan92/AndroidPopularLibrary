package ru.geekbrains.usefullibraries.mvp.model.repo.usercache;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.paperdb.Paper;
import io.reactivex.Single;
import ru.geekbrains.usefullibraries.util.Utils;
import ru.geekbrains.usefullibraries.mvp.model.entity.Repository;
import ru.geekbrains.usefullibraries.mvp.model.entity.User;

@Singleton
public final class PaperUserCache implements UserCache {

    @Inject
    PaperUserCache() {
    }

    @Override
    public void saveUser(User user) {
        Paper.book("users").write(user.getLogin(), user);
    }

    @Override
    public Single<User> getCachedUser(String username) {
        if (!Paper.book("users").contains(username)) {
            return Single.error(new RuntimeException("No such user in cache"));
        }
        return Single.fromCallable(() -> Paper.book("users").read(username));
    }

    @Override
    public void saveUserRepos(User user, List<Repository> repos) {
        String sha1 = Utils.SHA1(user.getReposUrl());
        Paper.book("repos").write(sha1, repos);
    }

    @Override
    public Single<List<Repository>> getUserRepos(User user) {
        String sha1 = Utils.SHA1(user.getReposUrl());
        if (!Paper.book("repos").contains(sha1)) {
            return Single.error(new RuntimeException("No repos for such user in cache"));
        }
        return Single.fromCallable(() -> Paper.book("repos").read(sha1));
    }
}
