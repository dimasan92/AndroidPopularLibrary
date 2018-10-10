package ru.geekbrains.usefullibraries;


import android.app.Application;

import com.activeandroid.ActiveAndroid;

import io.paperdb.Paper;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import ru.geekbrains.usefullibraries.mvp.di.AppComponent;
import ru.geekbrains.usefullibraries.mvp.di.DaggerAppComponent;
import timber.log.Timber;

public final class App extends Application {

    private static App instance;

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        Timber.plant(new Timber.DebugTree());
        Paper.init(this);

        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);

        appComponent = DaggerAppComponent.builder()
                .setContext(getApplicationContext())
                .build();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        ActiveAndroid.dispose();
    }

    public static App getInstance() {
        return instance;
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
