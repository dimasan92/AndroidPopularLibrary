package ru.geekbrains.usefullibraries.mvp.di.modules;

import android.widget.ImageView;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import ru.geekbrains.usefullibraries.mvp.model.image.ImageLoader;
import ru.geekbrains.usefullibraries.mvp.model.image.android.ImageLoaderGlide;
import ru.geekbrains.usefullibraries.mvp.model.image.android.ImageLoaderPicasso;

@Module(includes = CacheModule.class)
public interface LoaderModule {

    @Named("picasso_loader")
    @Singleton
    @Binds
    ImageLoader<ImageView> providePicassoImageLoader(ImageLoaderPicasso loader);

    @Named("glide_loader")
    @Singleton
    @Binds
    ImageLoader<ImageView> provideGlideImageLoader(ImageLoaderGlide loader);
}
