package ru.geekbrains.usefullibraries;

import java.io.IOException;
import java.io.InputStream;

public interface ImageConverter {

    boolean convertImage(InputStream stream) throws IOException;
}
