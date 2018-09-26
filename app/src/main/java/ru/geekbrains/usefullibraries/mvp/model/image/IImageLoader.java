package ru.geekbrains.usefullibraries.mvp.model.image;

public interface IImageLoader<T> {

    void loadInto(String url, T container);
}
