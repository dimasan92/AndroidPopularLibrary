package ru.geekbrains.usefullibraries.mvp.model.entity;

import com.google.gson.annotations.Expose;

public final class Repo {

    @Expose
    private final String name;

    public Repo(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
