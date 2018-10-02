package ru.geekbrains.usefullibraries.mvp.model.entity.activeandroid;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "repositories")
public final class AARepository extends Model {

    @Column(name = "github_id")
    public String id;

    @Column(name = "name")
    public String name;

    @Column(name = "user")
    public AAUser user;
}
