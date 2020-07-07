package com.github.PetrIlya.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {
    private PropertiesUtil() {

    }

    /**
     * @param initialSize      Initial properties capacity
     * @param pathToProperties Absolute path to properties
     * @return properties of given path
     */
    public static Properties getProperties(final int initialSize,
                                           final String pathToProperties) throws IOException {
        if (pathToProperties.isEmpty() || initialSize <= 0) {
            throw new IllegalArgumentException("Invalid argument");
        }
        final Properties properties = new Properties(initialSize);
        try (final InputStream propertiesStream = new FileInputStream(pathToProperties)) {
            properties.load(propertiesStream);
            return properties;
        }
    }
}
