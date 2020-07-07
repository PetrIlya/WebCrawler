package com.github.PetrIlya.util;

import com.github.PetrIlya.net.CrawlURL;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.util.Map;

@RunWith(MockitoJUnitRunner.class)
public class CSVUtilTest {
    @Mock
    public static Map<CrawlURL, Map<String, Integer>> statistic;
    public static String[] headers = {"Head"};

    @Test(expected = NullPointerException.class)
    public void writeToFileLexemeStatistic_NullTest() throws IOException {
        CSVUtil.writeToFileLexemeStatistic(null, null, "gtg");
    }

    @Test(expected = IOException.class)
    public void writeToFileLexemeStatistic_InvalidPathTest() throws IOException {
        CSVUtil.
                writeToFileLexemeStatistic(statistic,
                                           headers,
                                           "::");
    }
}