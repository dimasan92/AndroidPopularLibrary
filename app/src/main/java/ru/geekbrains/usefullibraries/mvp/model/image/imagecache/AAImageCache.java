package ru.geekbrains.usefullibraries.mvp.model.image.imagecache;

import android.graphics.Bitmap;

import com.activeandroid.query.Select;

import java.io.File;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;
import ru.geekbrains.usefullibraries.mvp.model.entity.activeandroid.AAAvatar;
import ru.geekbrains.usefullibraries.util.FilesUtils;
import ru.geekbrains.usefullibraries.util.Utils;

@Singleton
public final class AAImageCache implements ImageCache {

    private static final String SAVED_AVATAR_PREFIX = "Avatar_Image_";

    private final FilesUtils filesUtils;

    @Inject
    AAImageCache(FilesUtils filesUtils) {
        this.filesUtils = filesUtils;
    }

    @Override
    public void saveAvatar(Bitmap bitmap, String url) {
        final String shaUrl = Utils.SHA1(url);
        AAAvatar avatar = new Select()
                .from(AAAvatar.class)
                .where("avatar_url = ?", shaUrl)
                .executeSingle();

        if (avatar == null) {
            final String fileFormat = url.contains(".jpg") ? ".jpg" : ".png";
            final String fileName = SAVED_AVATAR_PREFIX + shaUrl + fileFormat;
            avatar = new AAAvatar(shaUrl, fileName);
            avatar.save();
        }
        filesUtils.writeBitmapToDisk(avatar.getFileName(), bitmap);
    }

    @Override
    public Single<File> getCachedAvatar(String url) {
        return Single.create(emitter -> {
            final String shaUrl = Utils.SHA1(url);
            final AAAvatar avatar = new Select()
                    .from(AAAvatar.class)
                    .where("avatar_url = ?", shaUrl)
                    .executeSingle();
            if (avatar == null) {
                emitter.onError(new RuntimeException("No such avatar in cache"));
            } else {
                emitter.onSuccess(filesUtils.getFileBitmapFromDisk(avatar.getFileName()));
            }
        });
    }
}
