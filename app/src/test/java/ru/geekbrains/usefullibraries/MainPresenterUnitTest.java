package ru.geekbrains.usefullibraries;


import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import io.reactivex.Single;
import io.reactivex.schedulers.TestScheduler;
import ru.geekbrains.usefullibraries.di.DaggerTestComponent;
import ru.geekbrains.usefullibraries.di.TestComponent;
import ru.geekbrains.usefullibraries.di.modules.TestRepoModule;
import ru.geekbrains.usefullibraries.mvp.model.api.UserRepo;
import ru.geekbrains.usefullibraries.mvp.model.entity.User;
import ru.geekbrains.usefullibraries.mvp.presenter.MainPresenter;
import ru.geekbrains.usefullibraries.mvp.view.MainView;

public class MainPresenterUnitTest {

    private MainPresenter presenter;
    private TestScheduler testScheduler;

    @Mock
    MainView mainView;

    @BeforeClass
    public static void setupClass() {
    }

    @AfterClass
    public static void tearDown() {
    }

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        testScheduler = new TestScheduler();
        presenter = Mockito.spy(new MainPresenter(testScheduler));
    }

    @After
    public void after() {
    }

    @Test
    public void loadInfoSuccessTest() {
        User user = new User("googlesamples", "avatarUrl", "reposUrl");
        TestComponent testComponent = DaggerTestComponent.builder()
                .testRepoModule(new TestRepoModule() {
                    @Override
                    public UserRepo provideUserRepo() {
                        UserRepo userRepo = super.provideUserRepo();
                        Mockito.when(userRepo.getUser("googlesamples")).thenReturn(Single.just(user));
                        Mockito.when(userRepo.getUserRepos(user)).thenReturn(Single.just(new ArrayList<>()));
                        return userRepo;
                    }
                })
                .build();

        testComponent.inject(presenter);
        presenter.attachView(mainView);
        presenter.onPermissionsGranted();
        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS);

        Mockito.verify(mainView).hideLoading();
        Mockito.verify(mainView).setUsername(user.getLogin());
        Mockito.verify(mainView).showAvatar(user.getAvatarUrl());
        Mockito.verify(mainView).updateRepoList();
    }
}
