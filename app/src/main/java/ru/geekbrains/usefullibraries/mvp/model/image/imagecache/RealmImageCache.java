package ru.geekbrains.usefullibraries.mvp.model.image.imagecache;

import android.graphics.Bitmap;

import java.io.File;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;
import io.realm.Realm;
import ru.geekbrains.usefullibraries.mvp.model.entity.realm.RealmAvatar;
import ru.geekbrains.usefullibraries.util.FilesUtils;
import ru.geekbrains.usefullibraries.util.Utils;

@Singleton
public final class RealmImageCache implements ImageCache {

    private static final String SAVED_AVATAR_PREFIX = "Avatar_Image_";

    private final FilesUtils filesUtils;

    @Inject
    RealmImageCache(FilesUtils filesUtils) {
        this.filesUtils = filesUtils;
    }

    @Override
    public void saveAvatar(Bitmap bitmap, String url) {
        final String fileFormat = url.contains(".jpg") ? ".jpg" : ".png";
        final String fileName = SAVED_AVATAR_PREFIX + Utils.SHA1(url) + fileFormat;

        final Realm realm = Realm.getDefaultInstance();
        final RealmAvatar realmAvatar = realm.where(RealmAvatar.class)
                .equalTo("url", url).findFirst();

        if (realmAvatar == null) {
            realm.executeTransaction(innerRealm -> {
                RealmAvatar newAvatar = realm.createObject(RealmAvatar.class, url);
                newAvatar.setFileName(fileName);
            });
        }
        filesUtils.writeBitmapToDisk(fileName, bitmap);
        realm.close();
    }

    @Override
    public Single<File> getCachedAvatar(String url) {
        return Single.create(emitter -> {
            final Realm realm = Realm.getDefaultInstance();
            final RealmAvatar avatar = realm.where(RealmAvatar.class)
                    .equalTo("url", url).findFirst();
            if (avatar == null) {
                emitter.onError(new RuntimeException("No such avatar in cache"));
            } else {
                emitter.onSuccess(filesUtils.getFileBitmapFromDisk(avatar.getFileName()));
            }
            realm.close();
        });
    }
}
