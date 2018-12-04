package ru.geekbrains.usefullibraries.mvp.model.image.android;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import javax.inject.Inject;
import javax.inject.Singleton;

import ru.geekbrains.usefullibraries.mvp.model.image.ImageLoader;

@Singleton
public final class ImageLoaderPicasso implements ImageLoader<ImageView> {

    @Inject
    ImageLoaderPicasso() {
    }

    @Override
    public void loadInto(@Nullable String url, ImageView container) {
        Picasso.get().load(url).into(container);
    }
}
