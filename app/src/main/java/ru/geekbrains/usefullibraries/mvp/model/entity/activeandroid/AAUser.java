package ru.geekbrains.usefullibraries.mvp.model.entity.activeandroid;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.List;

@Table(name = "users")
public final class AAUser extends Model {

    @Column(name = "login")
    public String login;

    @Column(name = "avatar_url")
    public String avatarUrl;

    @Column(name = "repos_url")
    public String reposUrl;

    public List<AARepository> repositories() {
        return getMany(AARepository.class, "user");
    }
}
