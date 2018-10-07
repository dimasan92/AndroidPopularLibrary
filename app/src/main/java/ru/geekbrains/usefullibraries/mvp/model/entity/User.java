package ru.geekbrains.usefullibraries.mvp.model.entity;

import java.util.ArrayList;
import java.util.List;

public final class User {

    private final String login;
    private final String avatarUrl;
    private final String reposUrl;
    private List<Repository> repos = new ArrayList<>();

    public User(String login, String avatarUrl, String reposUrl) {
        this.login = login;
        this.avatarUrl = avatarUrl;
        this.reposUrl = reposUrl;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public String getLogin() {
        return login;
    }

    public String getReposUrl() {
        return reposUrl;
    }

    public List<Repository> getRepos() {
        return repos;
    }

    public void setRepos(List<Repository> repos) {
        this.repos = repos;
    }
}
