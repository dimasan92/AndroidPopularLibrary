package ru.geekbrains.usefullibraries.mvp.model.image.android;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ru.geekbrains.usefullibraries.mvp.model.NetworkStatus;
import ru.geekbrains.usefullibraries.mvp.model.image.ImageLoader;
import ru.geekbrains.usefullibraries.mvp.model.image.imagecache.ImageCache;
import ru.geekbrains.usefullibraries.mvp.model.image.imagecache.RealmImageCache;
import ru.geekbrains.usefullibraries.util.FilesUtils;

@Singleton
public final class ImageLoaderGlide implements ImageLoader<ImageView> {

    private final ImageCache imageCache;

    @Inject
    ImageLoaderGlide(@Named("realm_image_cache") ImageCache imageCache) {
        this.imageCache = imageCache;
    }

    @SuppressLint("CheckResult")
    @Override
    public void loadInto(@Nullable String url, ImageView container) {
        if (NetworkStatus.isOnline()) {
            GlideApp.with(container.getContext()).asBitmap().load(url).listener(new RequestListener<Bitmap>() {

                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model,
                                            Target<Bitmap> target, boolean isFirstResource) {
                    return false;
                }

                @Override
                public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target,
                                               DataSource dataSource, boolean isFirstResource) {
                    imageCache.saveAvatar(resource, url);
                    return false;
                }
            }).into(container);
        } else {
            imageCache.getCachedAvatar(url)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(file -> GlideApp.with(container.getContext())
                                    .load(file)
                                    .into(container),
                            Throwable::printStackTrace);
        }
    }
}
