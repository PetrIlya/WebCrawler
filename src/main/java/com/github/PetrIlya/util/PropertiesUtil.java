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
        if (pathToProperties.isEmpty()) {
            throw new IllegalArgumentException("Path to the configuration file doesn't specified");
        }
        final Properties properties = new Properties(initialSize);
        try (final InputStream propertiesStream = new FileInputStream(pathToProperties)) {
            final String pathToResource = PathUtil.getAbsolutePathToResource(pathToProperties);
            properties.load(propertiesStream);
            return properties;
        }
    }
}
