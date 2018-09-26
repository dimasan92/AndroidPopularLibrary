package ru.geekbrains.usefullibraries.mvp.model.entity;

import com.google.gson.annotations.Expose;

public final class User {

    @Expose
    private final String login;
    @Expose
    private final String avatarUrl;

    public User(String login, String avatarUrl) {
        this.login = login;
        this.avatarUrl = avatarUrl;
    }

    public String getLogin() {
        return login;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }
}
