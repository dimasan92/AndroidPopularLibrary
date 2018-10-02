package ru.geekbrains.usefullibraries.mvp.model.entity.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public final class RealmRepository extends RealmObject {

    @PrimaryKey
    public String id;
    public String name;
}
