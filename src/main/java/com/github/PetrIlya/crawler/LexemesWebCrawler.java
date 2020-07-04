package com.github.PetrIlya.crawler;

import com.github.PetrIlya.net.CrawlURL;
import com.github.PetrIlya.net.LexemeStatistic;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class LexemesWebCrawler extends WebCrawler {
    private final Set<String> lexemes;
    private final Set<CrawlURL> visited;
    private final LexemeStatistic statistic;
    private final ExecutorService executorService;

    public LexemesWebCrawler(int maxDepth,
                             int maxPagesVisited,
                             String seed,
                             Set<String> lexemes) {
        super(maxDepth, maxPagesVisited, seed);
        this.lexemes = lexemes;
        this.visited = new ConcurrentSkipListSet<>();
        this.executorService = Executors.newSingleThreadExecutor();
        this.statistic = new LexemeStatistic(this.lexemes);
    }

    @Override
    public CompletableFuture<Void> startCrawlAsync() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void startCrawl() {
        CrawlURL seedURL = new CrawlURL(getSeed(),
                                        0);
        diveTo(seedURL);
    }

    private void diveTo(CrawlURL url) {
        try {
            url.getOutgoingURLs().
                    stream().
                    filter(outURL -> !visited.contains(url)).
                    filter(outURL -> outURL.getUrl().
                            startsWith(url.getUrl())).
                    collect(Collectors.toSet()).
                    forEach(outURL -> {
                        if (processURL(url)) {
                            diveTo(url);
                        }
                    });
        } catch (IOException e) {
            return;
        }
    }

    public boolean processURL(CrawlURL url) {
        if (this.pagesVisited.get() != getMaxPagesVisited() &&
                url.getDepth() <= getMaxDepth()) {
            pagesVisited.incrementAndGet();
            visited.add(url);
            statistic.addToStatistic(url);
            return true;
        }
        return false;
    }
}
