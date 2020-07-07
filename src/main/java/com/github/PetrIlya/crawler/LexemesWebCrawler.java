package com.github.PetrIlya.crawler;

import com.github.PetrIlya.net.CrawlURL;
import com.github.PetrIlya.net.LexemeStatistic;

import java.io.IOException;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.stream.Collectors;

public class LexemesWebCrawler extends WebCrawler {
    private final Set<CrawlURL> visited;
    private final LexemeStatistic statistic;
    private final Queue<CrawlURL> toVisit;

    public LexemesWebCrawler(int maxDepth,
                             int maxPagesVisited,
                             String seed,
                             Set<String> lexemes) {
        super(maxDepth, maxPagesVisited, seed);
        this.visited = new ConcurrentSkipListSet<>();
        this.statistic = new LexemeStatistic(lexemes);
        this.toVisit = new LinkedBlockingDeque<>();
    }

    public LexemesWebCrawler(int maxDepth,
                             int maxPagesVisited,
                             String seed,
                             LexemeStatistic statistic) {
        super(maxDepth, maxPagesVisited, seed);
        this.statistic = statistic;
        this.visited = new ConcurrentSkipListSet<>();
        this.toVisit = new LinkedBlockingDeque<>();
    }

    /**
     * Start crawling in vlocking mode
     * Adds seed to queue and processes with {@code processURL}
     * If there are outgoing url the'll be added to the queue
     */
    @Override
    public void startCrawl() {
        toVisit.add(new CrawlURL(this.getSeed(),
                                 0));
        while (true) {
            CrawlURL url = toVisit.poll();
            if (url == null ||
                    toVisit.size() >= maxPagesVisited) {
                break;
            }
            if (processURL(url)) {
                try {
                    toVisit.
                            addAll(url.getOutgoingURLs().
                                    stream().
                                    filter(out -> !visited.contains(out)).
                                    collect(Collectors.toList()));
                } catch (IOException e) {
                    continue;
                }
            } else {
                break;
            }
        }
        while (processURL(toVisit.poll())) ;
    }

    /**
     * Processes given URL (Add to statistic and crawler stats)
     *
     * @param url URL to process
     * @return Should next URL be processed
     */
    protected boolean processURL(CrawlURL url) {
        if (url == null ||
                this.pagesVisited.getAndIncrement() == getMaxPagesVisited() ||
                url.getDepth() >= getMaxDepth()) {
            return false;
        }
        visited.add(url);
        statistic.addToStatistic(url);
        return true;
    }

    public LexemeStatistic getStatistic() {
        return statistic;
    }
}
