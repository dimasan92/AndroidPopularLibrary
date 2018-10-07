package ru.geekbrains.usefullibraries.mvp.model.repo.usercache;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;
import ru.geekbrains.usefullibraries.mvp.model.entity.Repository;
import ru.geekbrains.usefullibraries.mvp.model.entity.User;
import ru.geekbrains.usefullibraries.mvp.model.entity.activeandroid.AARepository;
import ru.geekbrains.usefullibraries.mvp.model.entity.activeandroid.AAUser;

@Singleton
public final class AAUserCache implements UserCache {

    @Inject
    AAUserCache() {
    }

    @Override
    public void saveUser(User user) {
        new AAUser(user.getLogin(), user.getAvatarUrl(), user.getReposUrl()).save();
    }

    @Override
    public Single<User> getCachedUser(String username) {
        return Single.create(emitter -> {
            final AAUser aaUser = new Select()
                    .from(AAUser.class)
                    .where("login = ?", username)
                    .executeSingle();

            if (aaUser == null) {
                emitter.onError(new RuntimeException("No such user in cache"));
            } else {
                emitter.onSuccess(new User(aaUser.getLogin(), aaUser.getAvatarUrl(), aaUser.getReposUrl()));
            }
        });
    }

    @Override
    public void saveUserRepos(User user, List<Repository> repos) {
        AAUser aaUser = new Select()
                .from(AAUser.class)
                .where("login = ?", user.getLogin())
                .executeSingle();

        if (aaUser == null) {
            aaUser = new AAUser(user.getLogin(), user.getAvatarUrl(), user.getReposUrl());
            aaUser.save();
        }

        new Delete().from(AARepository.class).where("user = ?", aaUser.getId()).execute();

        ActiveAndroid.beginTransaction();
        try {
            for (Repository repository : repos) {
                new AARepository(repository.getId(), repository.getName(), aaUser).save();
            }
            ActiveAndroid.setTransactionSuccessful();
        } finally {
            ActiveAndroid.endTransaction();
        }
    }

    @Override
    public Single<List<Repository>> getUserRepos(User user) {
        return Single.create(emitter -> {
            final AAUser aaUser = new Select()
                    .from(AAUser.class)
                    .where("login = ?", user.getLogin())
                    .executeSingle();

            if (aaUser == null) {
                emitter.onError(new RuntimeException("No such user in cache"));
            } else {
                final List<Repository> repos = new ArrayList<>();
                for (AARepository aaRepository : aaUser.getRepositories()) {
                    repos.add(new Repository(aaRepository.getGithubId(), aaRepository.getName()));
                }
                emitter.onSuccess(repos);
            }
        });
    }
}
