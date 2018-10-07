package ru.geekbrains.usefullibraries.mvp.model.entity;

public final class Repository {

    private final String id;
    private final String name;

    public Repository(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
