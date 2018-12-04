package ru.geekbrains.usefullibraries.utils;

import android.graphics.Bitmap;

import java.io.File;

import javax.inject.Inject;
import javax.inject.Singleton;

import ru.geekbrains.usefullibraries.util.FilesUtils;

@Singleton
public class TestFilesUtils implements FilesUtils {

    @Inject
    TestFilesUtils() {
    }

    @Override
    public void writeBitmapToDisk(String filename, Bitmap bitmap) {
    }

    @Override
    public File getFileBitmapFromDisk(String filename) {
        throw new RuntimeException("File return");
    }
}
