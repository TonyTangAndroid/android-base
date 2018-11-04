package com.jordifierro.androidbase.data;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class TestUtils {

    public static File file(String fileName, Object obj) {
        ClassLoader classLoader = obj.getClass().getClassLoader();
        URL resource = classLoader.getResource(fileName);
        assert resource != null;
        return new File(resource.getPath());
    }

    public static String json(String s, Object obj) throws IOException {
        return FileUtils.readFileToString(file(s, obj), "UTF-8");
    }

}
