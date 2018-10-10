package ru.geekbrains.usefullibraries;

import android.graphics.Bitmap;
import android.support.test.InstrumentationRegistry;

import org.junit.Before;
import org.junit.Test;

import java.io.File;

import javax.inject.Inject;
import javax.inject.Named;

import io.paperdb.Paper;
import io.reactivex.observers.TestObserver;
import ru.geekbrains.usefullibraries.di.DaggerTestComponent;
import ru.geekbrains.usefullibraries.mvp.model.image.imagecache.ImageCache;

import static org.junit.Assert.assertEquals;

public class PaperImageCacheInstrumentedTest {

    @Named("paper_image_cache")
    @Inject
    ImageCache cache;

    @Before
    public void setup() {
        Paper.init(InstrumentationRegistry.getTargetContext());
        Paper.book().destroy();
        DaggerTestComponent.create().inject(this);
    }

    @Test
    public void saveAndGetAvatarTest() {
        final Bitmap bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ALPHA_8);
        final String avatarUrl = "some_avatar_url";

        cache.saveAvatar(bitmap, avatarUrl);

        TestObserver<File> observer = TestObserver.create();
        cache.getCachedAvatar(avatarUrl).subscribe(observer);

        observer.awaitTerminalEvent();

        assertEquals(observer.errors().get(0).getMessage(), "File return");
    }
}
