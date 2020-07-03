package com.github.PetrIlya.crawler;

public abstract class CrawlerConfig {
    public static final int DEFAULT_VARIABLES_AMOUNT = 4;
    public static final int DEFAULT_MAX_DEPTH = 8;
    public static final int DEFAULT_MAX_PAGES_VISITED = 10_000;

    public abstract WebCrawler getWebCrawler();
}
