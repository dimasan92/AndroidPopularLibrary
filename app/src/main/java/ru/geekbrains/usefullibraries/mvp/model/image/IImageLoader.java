package ru.geekbrains.usefullibraries.mvp.model.image;

public interface IImageLoader<T> {

    void loadInto(final String url, final T container);
}
