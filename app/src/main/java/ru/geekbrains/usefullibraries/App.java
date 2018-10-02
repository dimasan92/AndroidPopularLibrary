package ru.geekbrains.usefullibraries;

import android.app.Application;

import com.activeandroid.ActiveAndroid;

import io.paperdb.Paper;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import timber.log.Timber;

public final class App extends Application {

    private static App instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        Timber.plant(new Timber.DebugTree());
        Paper.init(this);
        ActiveAndroid.initialize(this);

        RealmConfiguration config = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);
        Realm.init(this);
    }

    public static App getInstance() {
        return instance;
    }
}
