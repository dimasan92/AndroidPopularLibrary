package ru.geekbrains.usefullibraries.mvp.model.repo.imagecache;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import io.reactivex.Single;
import io.realm.Realm;
import ru.geekbrains.usefullibraries.Utils;
import ru.geekbrains.usefullibraries.mvp.model.entity.realm.RealmImage;

public final class RealmImageCache implements IImageCache {

    @Override
    public Single<File> getCachedAvatar(String url, Context context) {
        return Single.create(emitter -> {
            final Realm realm = Realm.getDefaultInstance();
            final RealmImage realmImage = realm.where(RealmImage.class)
                    .equalTo("url", url).findFirst();
            if (realmImage == null) {
                emitter.onError(new RuntimeException("No such avatar in cache"));
            } else {
                emitter.onSuccess(new File(context.getFilesDir(), realmImage.fileName));
            }
            realm.close();
        });
    }

    @Override
    public void saveAvatar(Bitmap bitmap, Context context, String url) {
        final Realm realm = Realm.getDefaultInstance();
        final RealmImage realmImage = realm.where(RealmImage.class)
                .equalTo("url", url).findFirst();
        String fileName = Utils.SHA1(url);

        if (realmImage == null) {
            realm.executeTransaction(innerRealm -> {
                RealmImage newRealmImage = innerRealm.createObject(RealmImage.class, url);
                newRealmImage.fileName = fileName;
            });
        } else {
            realmImage.fileName = fileName;
        }
        try (OutputStream outputStream = context.openFileOutput(fileName, Activity.MODE_PRIVATE)) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            outputStream.write(stream.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
