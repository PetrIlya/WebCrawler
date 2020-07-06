package com.github.PetrIlya.net;

import org.junit.Test;

import java.io.IOException;
import java.io.UncheckedIOException;

import static org.junit.Assert.assertFalse;

public class CrawlURLTest {

    @Test(expected = UncheckedIOException.class)
    public void getOutgoingURLs_InvalidURLTest() {
        try {
            new CrawlURL("https://en.wedia.org/Wikip", 0).getOutgoingURLs();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Test
    public void getOutgoingURLs_CorrectURLTest() {
        try {
            CrawlURL crawlURL = new CrawlURL("https://en.wikipedia.org/wiki/Wikipedia",
                                             0);
            assertFalse(crawlURL.getOutgoingURLs().isEmpty());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}