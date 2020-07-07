package com.github.PetrIlya.crawler;

import com.github.PetrIlya.net.CrawlURL;

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

    /**
     * Start crawling in vlocking mode
     */
    public abstract void startCrawl();

    public int getMaxDepth() {
        return maxDepth;
    }

    /**
     * Processes given URL (Add to statistic and crawler stats)
     *
     * @param url URL to process
     * @return Should next URL be processed
     */
    protected abstract boolean processURL(CrawlURL url);

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
