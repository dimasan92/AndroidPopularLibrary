package ru.geekbrains.usefullibraries.mvp.model.repo.cache;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.realm.Realm;
import ru.geekbrains.usefullibraries.mvp.model.entity.Repository;
import ru.geekbrains.usefullibraries.mvp.model.entity.User;
import ru.geekbrains.usefullibraries.mvp.model.entity.realm.RealmRepository;
import ru.geekbrains.usefullibraries.mvp.model.entity.realm.RealmUser;

public final class RealmUserCache implements IUserCache {

    @Override
    public void saveUser(User user, String username) {
        final Realm realm = Realm.getDefaultInstance();
        final RealmUser realmUser = realm.where(RealmUser.class).equalTo("login", username).findFirst();

        if (realmUser == null) {
            realm.executeTransaction(innerRealm -> {
                RealmUser newRealmUser = innerRealm.createObject(RealmUser.class, username);
                newRealmUser.avatarUrl = user.getAvatarUrl();
                newRealmUser.reposUrl = user.getReposUrl();
            });
        } else {
            realm.executeTransaction(innerRealm -> {
                realmUser.avatarUrl = user.getAvatarUrl();
                realmUser.reposUrl = user.getReposUrl();
            });
        }
        realm.close();
    }

    @Override
    public Observable<User> getCachedUser(String username) {
        return Observable.create(emitter -> {
            final Realm realm = Realm.getDefaultInstance();
            final RealmUser realmUser = realm.where(RealmUser.class)
                    .equalTo("login", username).findFirst();
            if (realmUser == null) {
                emitter.onError(new RuntimeException("No such user in cache"));
            } else {
                emitter.onNext(new User(realmUser.login, realmUser.avatarUrl, realmUser.reposUrl));
                emitter.onComplete();
            }
            realm.close();
        });
    }

    @Override
    public void saveUserRepos(User user, List<Repository> repos) {
        final Realm realm = Realm.getDefaultInstance();
        final RealmUser realmUser = realm.where(RealmUser.class).equalTo("login", user.getLogin()).findFirst();
        if (realmUser == null) {
            realm.executeTransaction(innerRealm -> {
                RealmUser newRealmUser = innerRealm.createObject(RealmUser.class, user.getLogin());
                newRealmUser.avatarUrl = user.getAvatarUrl();
                newRealmUser.reposUrl = user.getReposUrl();
            });
        }
        realm.executeTransaction(innerRealm -> {
            realmUser.repos.deleteAllFromRealm();
            for (Repository repository : repos) {
                RealmRepository realmRepository = innerRealm.createObject(RealmRepository.class, repository.getId());
                realmRepository.name = repository.getName();
                realmUser.repos.add(realmRepository);
            }
        });
        realm.close();
    }

    @Override
    public Observable<List<Repository>> getUserRepos(User user) {
        return Observable.create(emitter -> {
            final Realm realm = Realm.getDefaultInstance();
            final RealmUser realmUser = realm.where(RealmUser.class)
                    .equalTo("login", user.getLogin()).findFirst();
            if (realmUser == null) {
                emitter.onError(new RuntimeException("No such user in cache"));
            } else {
                final List<Repository> repositories = new ArrayList<>();
                for (RealmRepository realmRepository : realmUser.repos) {
                    repositories.add(new Repository(realmRepository.id, realmRepository.name));
                }
                emitter.onNext(repositories);
                emitter.onComplete();
            }
            realm.close();
        });
    }
}
