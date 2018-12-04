package ru.geekbrains.usefullibraries.mvp.model.image;

import android.support.annotation.Nullable;

public interface ImageLoader<T> {

    void loadInto(@Nullable String url, T container);
}
