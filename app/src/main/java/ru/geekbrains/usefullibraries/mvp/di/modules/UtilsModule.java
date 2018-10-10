package ru.geekbrains.usefullibraries.mvp.di.modules;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import ru.geekbrains.usefullibraries.util.FilesUtils;
import ru.geekbrains.usefullibraries.util.FilesUtilsImpl;

@Module
public interface UtilsModule {

    @Singleton
    @Binds
    FilesUtils provideFilesUtils(FilesUtilsImpl filesUtils);
}
