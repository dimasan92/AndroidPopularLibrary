package ru.geekbrains.usefullibraries;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.observers.TestObserver;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import ru.geekbrains.usefullibraries.di.DaggerTestComponent;
import ru.geekbrains.usefullibraries.di.TestComponent;
import ru.geekbrains.usefullibraries.mvp.di.modules.ApiModule;
import ru.geekbrains.usefullibraries.mvp.model.api.UserRepo;
import ru.geekbrains.usefullibraries.mvp.model.entity.Repository;
import ru.geekbrains.usefullibraries.mvp.model.entity.User;

import static org.junit.Assert.assertEquals;

public class UserRepoInstrumentedTest {

    @Inject
    UserRepo userRepo;

    private static MockWebServer mockWebServer;

    @BeforeClass
    public static void setupClass() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
    }

    @AfterClass
    public static void tearDownClass() throws IOException {
        mockWebServer.shutdown();
    }

    @Before
    public void setup() {
        TestComponent component = DaggerTestComponent.builder()
                .apiModule(new ApiModule() {
                    @Override
                    public String baseUrl() {
                        return mockWebServer.url("/").toString();
                    }
                })
                .build();
        component.inject(this);
    }

    @Test
    public void getUser() {
        final String login = "some_login";
        final String avatarUrl = "some_avatar_url";
        final String reposUrl = "some_repos_url";

        mockWebServer.enqueue(createUserResponse(login, avatarUrl, reposUrl));
        TestObserver<User> observer = new TestObserver<>();
        userRepo.getUser(login).subscribe(observer);

        observer.awaitTerminalEvent();

        observer.assertValueCount(1);
        assertEquals(observer.values().get(0).getLogin(), login);
        assertEquals(observer.values().get(0).getAvatarUrl(), avatarUrl);
        assertEquals(observer.values().get(0).getReposUrl(), reposUrl);
    }

    @Test
    public void getUserRepos() {
        final int count = 10;
        final String repoName = "repo";

        final User user = new User("some_login", "some_avatar_url", "some_repos_url");

        mockWebServer.enqueue(createUserReposResponse(count, repoName));
        TestObserver<List<Repository>> observer = new TestObserver<>();
        userRepo.getUserRepos(user).subscribe(observer);

        observer.awaitTerminalEvent();

        observer.assertValueCount(1);
        List<Repository> repos = observer.values().get(0);
        for (int i = 0; i < count; i++) {
            assertEquals(repos.get(i).getId(), Integer.toString(i));
            assertEquals(repos.get(i).getName(), repoName + i);
        }
    }

    private MockResponse createUserResponse(String login, String avatarUrl, String reposUrl) {
        String body = "{\"login\": \"" + login + "\",\"avatar_url\": \"" + avatarUrl +
                "\",\"repos_url\": \"" + reposUrl + "\"}";
        return new MockResponse().setBody(body);
    }

    private MockResponse createUserReposResponse(int count, String repoName) {
        StringBuilder body = new StringBuilder("[");

        for (int i = 0; i < count; i++) {
            body.append("{\"id\":\"")
                    .append(i)
                    .append("\",\"name\":\"")
                    .append(repoName)
                    .append(i)
                    .append("\"},");
        }
        int length = body.length();
        body.replace(length - 1, length, "]");
        return new MockResponse().setBody(body.toString());
    }
}
