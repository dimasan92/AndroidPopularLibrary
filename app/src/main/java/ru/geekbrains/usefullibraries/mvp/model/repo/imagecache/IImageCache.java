package ru.geekbrains.usefullibraries.mvp.model.repo.imagecache;

import android.content.Context;
import android.graphics.Bitmap;

import java.io.File;

import io.reactivex.Single;

public interface IImageCache {

    Single<File> getCachedAvatar(String url, Context context);

    void saveAvatar(Bitmap bitmap, Context context, String url);
}
