package ru.geekbrains.usefullibraries.mvp.model.image.imagecache;

import android.graphics.Bitmap;

import java.io.File;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.paperdb.Paper;
import io.reactivex.Single;
import ru.geekbrains.usefullibraries.mvp.model.entity.Avatar;
import ru.geekbrains.usefullibraries.util.FilesUtils;
import ru.geekbrains.usefullibraries.util.Utils;

@Singleton
public final class PaperImageCache implements ImageCache {

    private static final String USERS_AVATAR_NAMESPACE = "users_avatar";
    private static final String SAVED_AVATAR_PREFIX = "Avatar_Image_";

    private final FilesUtils filesUtils;

    @Inject
    PaperImageCache(FilesUtils filesUtils) {
        this.filesUtils = filesUtils;
    }

    @Override
    public void saveAvatar(Bitmap bitmap, String url) {
        final String shaUrl = Utils.SHA1(url);
        Avatar avatar = Paper.book(USERS_AVATAR_NAMESPACE).read(shaUrl);
        if (avatar == null) {
            final String fileFormat = url.contains(".jpg") ? ".jpg" : ".png";
            final String fileName = SAVED_AVATAR_PREFIX + shaUrl + fileFormat;
            avatar = new Avatar(shaUrl, fileName);
            Paper.book(USERS_AVATAR_NAMESPACE).write(shaUrl, avatar);
        }
        filesUtils.writeBitmapToDisk(avatar.getFileName(), bitmap);
    }

    @Override
    public Single<File> getCachedAvatar(String url) {
        return Single.create(emitter -> {
            final String shaUrl = Utils.SHA1(url);
            Avatar avatar = Paper.book(USERS_AVATAR_NAMESPACE).read(shaUrl);
            if (avatar == null) {
                emitter.onError(new RuntimeException("No such avatar in cache"));
            } else {
                emitter.onSuccess(filesUtils.getFileBitmapFromDisk(avatar.getFileName()));
            }
        });
    }
}
