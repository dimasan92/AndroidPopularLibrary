package ru.geekbrains.usefullibraries.mvp.model.entity.activeandroid;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.List;

@Table(name = "users")
public final class AAUser extends Model {

    @Column(name = "login")
    private final String login;

    @Column(name = "avatar_url")
    private final String avatarUrl;

    @Column(name = "repos_url")
    private final String reposUrl;

    public AAUser(String login, String avatarUrl, String reposUrl) {
        super();
        this.login = login;
        this.avatarUrl = avatarUrl;
        this.reposUrl = reposUrl;
    }

    public String getLogin() {
        return login;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public String getReposUrl() {
        return reposUrl;
    }

    public List<AARepository> getRepositories() {
        return getMany(AARepository.class, "user");
    }
}
