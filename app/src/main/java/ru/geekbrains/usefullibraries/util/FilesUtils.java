package ru.geekbrains.usefullibraries.util;

import android.graphics.Bitmap;

import java.io.File;

public interface FilesUtils {

    void writeBitmapToDisk(String filename, Bitmap bitmap);

    File getFileBitmapFromDisk(String filename);
}
