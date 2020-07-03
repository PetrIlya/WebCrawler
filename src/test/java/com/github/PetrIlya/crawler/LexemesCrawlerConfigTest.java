package com.github.PetrIlya.crawler;

import org.junit.Assert;
import org.junit.Test;

import java.io.FileNotFoundException;

public class LexemesCrawlerConfigTest {

    @Test
    public void getInstance_WithCorrectProperties() throws FileNotFoundException {
        LexemesCrawlerConfig config = LexemesCrawlerConfig.getInstance("correct.properties");
        Assert.assertEquals(10, config.getMaxDepth());
        Assert.assertEquals(100, config.getMaxPagesVisited());
        Assert.assertEquals(6, config.getLexemesToSearch().size());
    }
}