package ru.geekbrains.usefullibraries.mvp.model.repo.cache;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import ru.geekbrains.usefullibraries.mvp.model.entity.Repository;
import ru.geekbrains.usefullibraries.mvp.model.entity.User;
import ru.geekbrains.usefullibraries.mvp.model.entity.activeandroid.AARepository;
import ru.geekbrains.usefullibraries.mvp.model.entity.activeandroid.AAUser;

public final class AAUserCache implements IUserCache {

    @Override
    public void saveUser(User user, String username) {
        AAUser aaUser = new Select()
                .from(AAUser.class)
                .where("login = ?", username)
                .executeSingle();
        if (aaUser == null) {
            aaUser = new AAUser();
            aaUser.login = username;
        }
        aaUser.avatarUrl = user.getAvatarUrl();
        aaUser.reposUrl = user.getReposUrl();
        aaUser.save();
    }

    @Override
    public Observable<User> getCachedUser(String username) {
        return Observable.create(emitter -> {
            final AAUser aaUser = new Select()
                    .from(AAUser.class)
                    .where("login = ?", username)
                    .executeSingle();

            if (aaUser == null) {
                emitter.onError(new RuntimeException("No such user in cache"));
            } else {
                emitter.onNext(new User(aaUser.login, aaUser.avatarUrl, aaUser.reposUrl));
                emitter.onComplete();
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
            aaUser = new AAUser();
            aaUser.login = user.getLogin();
            aaUser.avatarUrl = user.getAvatarUrl();
            aaUser.reposUrl = user.getReposUrl();
            aaUser.save();
        }

        new Delete().from(AARepository.class).where("user = ?", aaUser.getId()).execute();

        ActiveAndroid.beginTransaction();
        try {
            for (Repository repository : repos) {
                AARepository aaRepository = new AARepository();
                aaRepository.id = repository.getId();
                aaRepository.name = repository.getName();
                aaRepository.user = aaUser;
                aaRepository.save();
            }
            ActiveAndroid.setTransactionSuccessful();
        } finally {
            ActiveAndroid.endTransaction();
        }
    }

    @Override
    public Observable<List<Repository>> getUserRepos(User user) {
        return Observable.create(emitter -> {
            final AAUser aaUser = new Select()
                    .from(AAUser.class)
                    .where("login = ?", user.getLogin())
                    .executeSingle();

            if (aaUser == null) {
                emitter.onError(new RuntimeException("No such user in cache"));
            } else {
                List<Repository> repos = new ArrayList<>();
                for (AARepository aaRepository : aaUser.repositories()) {
                    repos.add(new Repository(aaRepository.id, aaRepository.name));
                }
                emitter.onNext(repos);
                emitter.onComplete();
            }
        });
    }
}
