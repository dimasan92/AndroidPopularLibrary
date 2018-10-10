package ru.geekbrains.usefullibraries;


import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import io.reactivex.schedulers.TestScheduler;
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
    public void loadInfoCall() {
    }
}
