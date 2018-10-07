package ru.geekbrains.usefullibraries.mvp.di;

import android.content.Context;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import ru.geekbrains.usefullibraries.mvp.di.modules.LoaderModule;
import ru.geekbrains.usefullibraries.mvp.di.modules.RepoModule;
import ru.geekbrains.usefullibraries.mvp.presenter.MainPresenter;
import ru.geekbrains.usefullibraries.ui.MainActivity;

@Singleton
@Component(modules = {RepoModule.class, LoaderModule.class})
public interface AppComponent {

    void inject(MainActivity activity);

    void inject(MainPresenter presenter);

    @Component.Builder
    interface Builder {
        AppComponent build();

        @BindsInstance
        Builder setContext(Context context);
    }
}
