package com.github.PetrIlya.util;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Properties;

import static org.junit.Assert.assertEquals;

public class PropertiesUtilTest {

    @Test
    public void getProperties_ValidCase() throws IOException {
        Properties properties = new Properties(1);
        properties.put("key", "value");
        String path = "file";
        File file = new File(path);
        file.createNewFile();
        Writer writer = new PrintWriter(path);
        properties.store(writer, "message");
        writer.flush();
        writer.close();
        Properties loaded = PropertiesUtil.
                getProperties(1, path);
        assertEquals(loaded, properties);
        file.delete();
    }

    @Test(expected = IllegalArgumentException.class)
    public void getProperties_IllegalArg() throws IOException {
        PropertiesUtil.getProperties(-1, "");
    }

}