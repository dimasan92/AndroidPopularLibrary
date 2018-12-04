package ru.geekbrains.usefullibraries.mvp.model.image.imagecache;

import android.graphics.Bitmap;

import java.io.File;

import io.reactivex.Single;

public interface ImageCache {

    void saveAvatar(Bitmap bitmap, String url);

    Single<File> getCachedAvatar(String url);
}
