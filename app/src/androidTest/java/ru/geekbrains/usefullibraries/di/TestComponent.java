package ru.geekbrains.usefullibraries.di;

import javax.inject.Singleton;

import dagger.Component;
import ru.geekbrains.usefullibraries.PaperUserCacheInstrumentedTest;
import ru.geekbrains.usefullibraries.RealmUserCacheInstrumentedTest;
import ru.geekbrains.usefullibraries.UserRepoInstrumentedTest;
import ru.geekbrains.usefullibraries.mvp.di.modules.CacheModule;
import ru.geekbrains.usefullibraries.mvp.di.modules.RepoModule;

@Singleton
@Component(modules = {RepoModule.class, CacheModule.class})
public interface TestComponent {

    void inject(UserRepoInstrumentedTest test);

    void inject(PaperUserCacheInstrumentedTest test);

    void inject(RealmUserCacheInstrumentedTest test);
}
