package ru.geekbrains.usefullibraries.mvp.model.repo.usercache;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;
import io.realm.Realm;
import ru.geekbrains.usefullibraries.mvp.model.entity.Repository;
import ru.geekbrains.usefullibraries.mvp.model.entity.User;
import ru.geekbrains.usefullibraries.mvp.model.entity.realm.RealmRepository;
import ru.geekbrains.usefullibraries.mvp.model.entity.realm.RealmUser;

@Singleton
public final class RealmUserCache implements UserCache {

    @Inject
    RealmUserCache() {
    }

    @Override
    public void saveUser(User user) {
        final Realm realm = Realm.getDefaultInstance();
        final RealmUser realmUser = realm.where(RealmUser.class)
                .equalTo("login", user.getLogin()).findFirst();

        if (realmUser == null) {
            realm.executeTransaction(innerRealm -> {
                RealmUser newRealmUser = innerRealm.createObject(RealmUser.class, user.getLogin());
                newRealmUser.setAvatarUrl(user.getAvatarUrl());
                newRealmUser.setReposUrl(user.getReposUrl());
            });
        } else {
            realm.executeTransaction(innerRealm -> {
                realmUser.setAvatarUrl(user.getAvatarUrl());
                realmUser.setReposUrl(user.getReposUrl());
            });
        }
        realm.close();
    }

    @Override
    public Single<User> getCachedUser(String username) {
        return Single.create(emitter -> {
            final Realm realm = Realm.getDefaultInstance();
            final RealmUser realmUser = realm.where(RealmUser.class)
                    .equalTo("login", username).findFirst();
            if (realmUser == null) {
                emitter.onError(new RuntimeException("No such user in cache"));
            } else {
                emitter.onSuccess(new User(realmUser.getLogin(), realmUser.getAvatarUrl(),
                        realmUser.getReposUrl()));
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
                newRealmUser.setAvatarUrl(user.getAvatarUrl());
                newRealmUser.setReposUrl(user.getReposUrl());
            });
        }
        realm.executeTransaction(innerRealm -> {
            realmUser.getRepos().deleteAllFromRealm();
            for (Repository repository : repos) {
                RealmRepository realmRepository = innerRealm.copyToRealm(
                        new RealmRepository(repository.getId(), repository.getName()));
                realmUser.getRepos().add(realmRepository);
            }
        });
        realm.close();
    }

    @Override
    public Single<List<Repository>> getUserRepos(User user) {
        return Single.create(emitter -> {
            final Realm realm = Realm.getDefaultInstance();
            final RealmUser realmUser = realm.where(RealmUser.class)
                    .equalTo("login", user.getLogin()).findFirst();
            if (realmUser == null) {
                emitter.onError(new RuntimeException("No such user in cache"));
            } else {
                final List<Repository> repositories = new ArrayList<>();
                for (RealmRepository realmRepository : realmUser.getRepos()) {
                    repositories.add(new Repository(realmRepository.getId(), realmRepository.getName()));
                }
                emitter.onSuccess(repositories);
            }
            realm.close();
        });
    }
}
