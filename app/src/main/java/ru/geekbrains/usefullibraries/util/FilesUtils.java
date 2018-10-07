package ru.geekbrains.usefullibraries.util;

import android.content.Context;
import android.graphics.Bitmap;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public final class FilesUtils {

    private static final String IMAGE_FOLDER_NAME = "user_avatars";

    private final Context appContext;

    @Inject
    public FilesUtils(Context appContext) {
        this.appContext = appContext;
    }

    public void writeBitmapToDisk(String filename, Bitmap bitmap) {
        if (!getImageDir().exists() && !getImageDir().mkdirs()) {
            throw new RuntimeException("Failed to create directory: " + getImageDir().toString());
        }
        final Bitmap.CompressFormat compressFormat
                = (filename.split("\\.")[1]).equals("jpg") ? Bitmap.CompressFormat.JPEG : Bitmap.CompressFormat.PNG;
        final File imageFile = new File(getImageDir(), filename);

        try (OutputStream outputStream = new FileOutputStream(imageFile)) {
            bitmap.compress(compressFormat, 100, outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public File getFileBitmapFromDisk(String filename) {
        if (!getImageDir().exists() && !getImageDir().mkdirs()) {
            throw new RuntimeException("Failed to create directory: " + getImageDir().toString());
        }
        return new File(getImageDir(), filename);
    }

    private File getImageDir() {
        return new File(appContext.getExternalFilesDir(null) + "/" + IMAGE_FOLDER_NAME);
    }
}
