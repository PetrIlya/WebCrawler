package com.github.PetrIlya;

import com.github.PetrIlya.crawler.LexemesCrawlerConfig;
import com.github.PetrIlya.crawler.LexemesWebCrawler;
import com.github.PetrIlya.net.CrawlURL;
import com.github.PetrIlya.util.CSVUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class App {
    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            System.exit(-1);
        }
        final LexemesWebCrawler crawler = LexemesCrawlerConfig.
                getInstance(args[0]).
                getWebCrawler();
        crawler.startCrawl();
        final Map<CrawlURL, Map<String, Integer>> statMap = crawler.getStatistic().getStatMap();
        final List<String> headers = new ArrayList<>(crawler.getStatistic().getLexemes().size() + 1);
        headers.addAll(crawler.getStatistic().getLexemes());
        headers.sort(String::compareTo);
        headers.add(0, "seed");
        final String[] arrHeaders = new String[headers.size()];
        headers.toArray(arrHeaders);
        CSVUtil.writeToFileLexemeStatistic(statMap,
                                           arrHeaders,
                                           "result");
        Comparator<Map.
                Entry<CrawlURL,
                Map<String, Integer>>> rule = (e1, e2) -> {
            int totalHintsE1 = e1.
                    getValue().
                    values().
                    stream().
                    reduce(0, Integer::sum);
            int totalHintsE2 = e2.
                    getValue().
                    values().
                    stream().
                    reduce(0, Integer::sum);
            return Integer.compare(totalHintsE1, totalHintsE2);
        };

        CSVUtil.writeToFileLexemeStatistic(crawler.
                                                   getStatistic().
                                                   getTopURLByRule(10, rule),
                                           arrHeaders,
                                           "best");
    }
}
