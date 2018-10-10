package ru.geekbrains.usefullibraries.di;

import javax.inject.Singleton;

import dagger.Component;
import ru.geekbrains.usefullibraries.UserRepoInstrumentedTest;
import ru.geekbrains.usefullibraries.mvp.di.modules.RepoModule;

@Singleton
@Component(modules = RepoModule.class)
public interface TestComponent {

    void inject(UserRepoInstrumentedTest test);
}
