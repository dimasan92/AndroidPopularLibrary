package ru.geekbrains.usefullibraries.mvp.di.modules;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import ru.geekbrains.usefullibraries.mvp.model.image.imagecache.AAImageCache;
import ru.geekbrains.usefullibraries.mvp.model.image.imagecache.ImageCache;
import ru.geekbrains.usefullibraries.mvp.model.image.imagecache.PaperImageCache;
import ru.geekbrains.usefullibraries.mvp.model.image.imagecache.RealmImageCache;
import ru.geekbrains.usefullibraries.mvp.model.repo.usercache.AAUserCache;
import ru.geekbrains.usefullibraries.mvp.model.repo.usercache.PaperUserCache;
import ru.geekbrains.usefullibraries.mvp.model.repo.usercache.RealmUserCache;
import ru.geekbrains.usefullibraries.mvp.model.repo.usercache.UserCache;

@Module
public interface CacheModule {

    @Named("realm_user_cache")
    @Singleton
    @Binds
    UserCache proviedeRealmUserCache(RealmUserCache cache);

    @Named("aa_user_cache")
    @Singleton
    @Binds
    UserCache proviedeAAUserCache(AAUserCache cache);

    @Named("paper_user_cache")
    @Singleton
    @Binds
    UserCache proviedePaperUserCache(PaperUserCache cache);

    @Named("realm_image_cache")
    @Singleton
    @Binds
    ImageCache proviedeRealmImageCache(RealmImageCache cache);

    @Named("aa_image_cache")
    @Singleton
    @Binds
    ImageCache proviedeAAImageCache(AAImageCache cache);

    @Named("paper_image_cache")
    @Singleton
    @Binds
    ImageCache proviedePaperImageCache(PaperImageCache cache);
}
