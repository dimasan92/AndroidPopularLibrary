package ru.geekbrains.usefullibraries.mvp.model.entity.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class RealmImage extends RealmObject {

    @PrimaryKey
    public String url;
    public String fileName;
}
