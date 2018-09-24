package ru.geekbrains.usefullibraries;

import java.io.InputStream;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

final class MainModel {

    private final ImageConverter converter;

    MainModel(ImageConverter converter) {
        this.converter = converter;
    }

    Single<Boolean> convertImage(InputStream stream) {
        return Single.fromCallable(() -> converter.convertImage(stream))
                .subscribeOn(Schedulers.computation());
    }
}
