package ru.geekbrains.usefullibraries.mvp.model.entity.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class RealmRepository extends RealmObject {

    @PrimaryKey
    private String id;
    private String name;

    public RealmRepository() {
    }

    public RealmRepository(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
