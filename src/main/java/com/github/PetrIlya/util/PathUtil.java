package com.github.PetrIlya.util;

import java.io.FileNotFoundException;
import java.net.URL;
import java.nio.file.Paths;

public final class PathUtil {

    private PathUtil() {

    }

    public static String getAbsolutePathToResource(String path) throws FileNotFoundException {
        if (Paths.get(path).isAbsolute()) {
            return path;
        }
        URL absolute = PathUtil.class.getClassLoader().getResource(path);
        if (absolute == null) {
            throw new FileNotFoundException();
        }
        return absolute.getFile().replaceFirst("/", "");
    }
}
