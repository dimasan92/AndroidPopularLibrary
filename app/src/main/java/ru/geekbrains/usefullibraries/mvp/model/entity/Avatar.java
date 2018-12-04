package ru.geekbrains.usefullibraries.mvp.model.entity;

public final class Avatar {
    private final String url;
    private final String fileName;

    public Avatar(String url, String fileName) {
        this.url = url;
        this.fileName = fileName;
    }

    public String getUrl() {
        return url;
    }

    public String getFileName() {
        return fileName;
    }
}
