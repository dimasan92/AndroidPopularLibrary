package ru.geekbrains.usefullibraries.mvp.model.image.android;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import ru.geekbrains.usefullibraries.mvp.model.image.ImageLoader;

public final class ImageLoaderPicasso implements ImageLoader<ImageView> {

    @Override
    public void loadInto(@Nullable String url, ImageView container) {
        Picasso.get().load(url).into(container);
    }
}
