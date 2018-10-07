package ru.geekbrains.usefullibraries.mvp.model.entity.activeandroid;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "avatars")
public final class AAAvatar extends Model {

    @Column(name = "avatar_url")
    private final String url;

    @Column(name = "avatar_filename")
    private final String fileName;

    public AAAvatar(String url, String fileName) {
        super();
        this.url = url;
        this.fileName = fileName;
    }

    public String getUrl() {
        return url;
    }

    public String getFileName() {
        return fileName;
    }
}
