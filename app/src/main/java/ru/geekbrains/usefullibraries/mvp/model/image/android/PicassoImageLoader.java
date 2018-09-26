package ru.geekbrains.usefullibraries.mvp.model.image.android;

import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import ru.geekbrains.usefullibraries.mvp.model.image.IImageLoader;

public final class PicassoImageLoader implements IImageLoader<ImageView> {

    @Override
    public void loadInto(String url, ImageView container) {
        Picasso.get().load(url).into(container);
    }
}
