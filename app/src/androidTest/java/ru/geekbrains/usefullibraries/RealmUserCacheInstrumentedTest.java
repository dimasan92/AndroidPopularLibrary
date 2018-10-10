package ru.geekbrains.usefullibraries;

import android.util.Log;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.observers.TestObserver;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import ru.geekbrains.usefullibraries.di.DaggerTestComponent;
import ru.geekbrains.usefullibraries.mvp.model.entity.Repository;
import ru.geekbrains.usefullibraries.mvp.model.entity.User;
import ru.geekbrains.usefullibraries.mvp.model.repo.usercache.RealmUserCache;

import static org.junit.Assert.assertEquals;

public class RealmUserCacheInstrumentedTest {

    @Inject
    RealmUserCache cache;

    @Before
    public void setup() {
        DaggerTestComponent.create().inject(this);
    }

    @Test
    public void saveAndGetUserTest() {
        final String login = "some_login";
        final String avatarUrl = "some_avatar_url";
        final String reposUrl = "some_repos_url";

        final User user = new User(login, avatarUrl, reposUrl);

        cache.saveUser(user);

        TestObserver<User> observer = TestObserver.create();
        cache.getCachedUser(login).subscribe(observer);

        observer.awaitTerminalEvent();
        observer.assertValueCount(1);

        assertEquals(observer.values().get(0).getLogin(), login);
        assertEquals(observer.values().get(0).getAvatarUrl(), avatarUrl);
        assertEquals(observer.values().get(0).getReposUrl(), reposUrl);
    }

    @Test
    public void saveAndGetUserRepos() {
        final int count = 10;
        final String repoName = "repo";

        final List<Repository> repos = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            repos.add(new Repository(Integer.toString(i), repoName + i));
        }

        final User user = new User("some_login", "some_avatar_url", "some_repos_url");
        cache.saveUserRepos(user, repos);

        TestObserver<List<Repository>> observer = TestObserver.create();
        cache.getUserRepos(user).subscribe(observer);

        observer.awaitTerminalEvent();
        observer.assertValueCount(1);

        List<Repository> cachedRepos = observer.values().get(0);
        for (int i = 0; i < count; i++) {
            assertEquals(repos.get(i).getId(), Integer.toString(i));
            assertEquals(repos.get(i).getName(), repoName + i);
        }
    }
}
