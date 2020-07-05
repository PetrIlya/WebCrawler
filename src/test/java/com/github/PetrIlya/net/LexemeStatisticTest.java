package com.github.PetrIlya.net;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;
import java.util.Set;

import static org.junit.Assert.*;

public class LexemeStatisticTest {
    public static final Set<String> lexemes = Set.of("SpaceX", "Must");

    public static LexemeStatistic statistic;

    @Before
    public void setUp() {
        statistic = new LexemeStatistic(lexemes);
    }

    @Test
    public void addToStatistic() {
        CrawlURL url1 = new CrawlURL("https://en.wikipedia.org/wiki/SpaceX", 0);
        statistic.addToStatistic(url1);
        CrawlURL url2 = new CrawlURL("https://habr.com/ru/post/130581", 0);
        statistic.addToStatistic(url2);
        assertTrue(statistic.getStatMap().containsKey(url1));
        assertTrue(statistic.getStatMap().containsKey(url2));
        assertFalse(statistic.getStatMap().get(url1).isEmpty());
        assertFalse(statistic.getStatMap().get(url2).isEmpty());
    }

    @Test
    public void getLexemesStatistic() {
        Map<String, Integer> stat = statistic.
                getLexemesStatistic(new CrawlURL("https://en.wikipedia.org/wiki/SpaceX", 0));
        lexemes.forEach(key -> Assert.assertTrue(stat.containsKey(key)));
        assertEquals(0, (int) stat.get("Must"));
    }

    @Test
    public void getAmountOfEntries_ValidCase() {
        Assert.assertEquals(statistic.getAmountOfEntries(new CrawlURL("https://en.wikipedia.org/wiki/SpaceX", 0),
                                                         "SpaceX"), 426);
    }

    @Test
    public void getAmountOfEntries_ExceptionCase() {
        Assert.assertEquals(statistic.getAmountOfEntries(new CrawlURL("https://en.widia.org/wiki/SpaceX", 0),
                                                         "SpaceX"), 0);
    }
}