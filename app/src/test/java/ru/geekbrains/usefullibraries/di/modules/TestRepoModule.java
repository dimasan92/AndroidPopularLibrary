package ru.geekbrains.usefullibraries.di.modules;

import org.mockito.Mockito;

import dagger.Module;
import dagger.Provides;
import ru.geekbrains.usefullibraries.mvp.model.api.UserRepo;

@Module
public class TestRepoModule {

    @Provides
    public UserRepo provideUserRepo() {
        return Mockito.mock(UserRepo.class);
    }
}
