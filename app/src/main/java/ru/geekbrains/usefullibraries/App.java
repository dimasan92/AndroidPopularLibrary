package ru.geekbrains.usefullibraries;

import android.app.Application;

import timber.log.Timber;

public final class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());
    }
}
