package ru.geekbrains.usefullibraries.mvp.model.entity.activeandroid;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "repositories")
public final class AARepository extends Model {

    @Column(name = "github_id")
    private final String githubId;

    @Column(name = "name")
    private final String name;

    @Column(name = "user")
    private final AAUser user;

    public AARepository(String githubId, String name, AAUser user) {
        super();
        this.githubId = githubId;
        this.name = name;
        this.user = user;
    }

    public String getGithubId() {
        return githubId;
    }

    public String getName() {
        return name;
    }

    public AAUser getUser() {
        return user;
    }
}
