package com.github.PetrIlya.crawler;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class WebCrawler {

    protected int maxDepth;
    protected int maxPagesVisited;
    protected String seed;

    protected AtomicInteger pagesVisited;

    public WebCrawler(int maxDepth,
                      int maxPagesVisited,
                      String seed) {
        this.maxDepth = maxDepth;
        this.maxPagesVisited = maxPagesVisited;
        this.seed = seed;
        this.pagesVisited = new AtomicInteger();
    }

    public abstract void startCrawl();

    public abstract CompletableFuture<Void> startCrawlAsync();

    public int getMaxDepth() {
        return maxDepth;
    }

    public void setMaxDepth(int maxDepth) {
        this.maxDepth = maxDepth;
    }

    public int getMaxPagesVisited() {
        return maxPagesVisited;
    }

    public void setMaxPagesVisited(int maxPagesVisited) {
        this.maxPagesVisited = maxPagesVisited;
    }

    public String getSeed() {
        return seed;
    }

    public void setSeed(String seed) {
        this.seed = seed;
    }
}
