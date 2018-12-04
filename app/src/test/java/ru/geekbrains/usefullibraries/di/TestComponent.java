package ru.geekbrains.usefullibraries.di;

import javax.inject.Singleton;

import dagger.Component;
import ru.geekbrains.usefullibraries.di.modules.TestRepoModule;
import ru.geekbrains.usefullibraries.mvp.presenter.MainPresenter;

@Singleton
@Component(modules = {TestRepoModule.class})
public interface TestComponent {

    void inject(MainPresenter presenter);
}
