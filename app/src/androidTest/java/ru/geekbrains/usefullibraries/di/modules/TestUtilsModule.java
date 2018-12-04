package ru.geekbrains.usefullibraries.di.modules;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import ru.geekbrains.usefullibraries.util.FilesUtils;
import ru.geekbrains.usefullibraries.util.FilesUtilsImpl;
import ru.geekbrains.usefullibraries.utils.TestFilesUtils;

@Module
public interface TestUtilsModule {

    @Singleton
    @Binds
    FilesUtils provideFilesUtils(TestFilesUtils filesUtils);
}
