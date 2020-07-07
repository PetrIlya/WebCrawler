package com.github.PetrIlya.util;

import org.junit.Test;

import java.io.FileNotFoundException;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;

public class PathUtilTest {

    @Test
    public void getAbsolutePathToResource_ValidCase() throws FileNotFoundException {
        Path path = Paths.get("").toAbsolutePath();
        String pathRet = PathUtil.getAbsolutePathToResource(path.toString());
        assertEquals(path.toString(), pathRet);
    }

    @Test(expected = InvalidPathException.class)
    public void getAbsolutePathToResource_ExceptionCase() throws FileNotFoundException {
        Path path = Paths.get("//stack").toAbsolutePath();
        PathUtil.getAbsolutePathToResource(path.toString());
    }

    @Test(expected = FileNotFoundException.class)
    public void getAbsolutePathToResource_FileNotFound() throws FileNotFoundException {
        String path = PathUtil.getAbsolutePathToResource("file");
    }
}