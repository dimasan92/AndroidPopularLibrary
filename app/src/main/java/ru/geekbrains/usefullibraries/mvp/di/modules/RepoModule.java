package ru.geekbrains.usefullibraries.mvp.di.modules;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import ru.geekbrains.usefullibraries.mvp.model.api.UserRepo;
import ru.geekbrains.usefullibraries.mvp.model.repo.UserRepoImpl;

@Module(includes = {ApiModule.class, CacheModule.class})
public interface RepoModule {

    @Singleton
    @Binds
    UserRepo userRepo(UserRepoImpl userRepo);
}
