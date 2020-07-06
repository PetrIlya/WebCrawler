package com.github.PetrIlya.crawler;

import org.junit.Assert;
import org.junit.Test;

import java.io.FileNotFoundException;

public class LexemesCrawlerConfigTest {

    @Test
    public void getInstance_WithCorrectProperties() throws FileNotFoundException {
        LexemesCrawlerConfig config = LexemesCrawlerConfig.getInstance("correct.properties");
        Assert.assertEquals(4, config.getMaxDepth());
        Assert.assertEquals(4, config.getMaxPagesVisited());
        Assert.assertEquals(6, config.getLexemesToSearch().size());
    }
}