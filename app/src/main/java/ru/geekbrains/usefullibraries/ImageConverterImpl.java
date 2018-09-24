package ru.geekbrains.usefullibraries;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public final class ImageConverterImpl implements ImageConverter {

    private static final int COMPRESS_QUALITY = 100;

    private final Context appContext;

    ImageConverterImpl(final Context context) {
        this.appContext = context.getApplicationContext();
    }

    @Override
    public boolean convertImage(InputStream stream) throws IOException {
        String fileName = UUID.randomUUID().toString() + ".png";
        boolean success = false;
        OutputStream outputStream = null;
        try {
            Bitmap bitmap = BitmapFactory.decodeStream(stream);
            Thread.sleep(5000);
            outputStream = appContext.openFileOutput(fileName, Context.MODE_PRIVATE);
            success = bitmap.compress(Bitmap.CompressFormat.PNG, COMPRESS_QUALITY,
                    outputStream);
            bitmap.recycle();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                outputStream.close();
            }
            stream.close();
        }
        return success;
    }
}
