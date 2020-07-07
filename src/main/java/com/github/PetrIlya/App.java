package com.github.PetrIlya;

import com.github.PetrIlya.crawler.LexemesCrawlerConfig;
import com.github.PetrIlya.crawler.LexemesWebCrawler;
import com.github.PetrIlya.net.CrawlURL;
import com.github.PetrIlya.util.CSVUtil;

import java.io.IOException;
import java.util.*;

public class App {
    public static void main(String[] args) throws IOException, InterruptedException {
        if (args.length == 0) {
            System.exit(-1);
        }
        final LexemesWebCrawler crawler = LexemesCrawlerConfig.
                getInstance(args[0]).
                getWebCrawler();
        crawler.startCrawl();
        final Map<CrawlURL, Map<String, Integer>> statMap = crawler.
                getStatistic().
                getStatMap();
        final String[] arrHeaders = prepareHeaders(crawler.getStatistic().getLexemes());
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
            return Integer.compare(totalHintsE2, totalHintsE1);
        };
        final Map<CrawlURL, Map<String, Integer>> best = new LinkedHashMap<>();
        crawler.
                getStatistic().
                getTopURLByRule(10, rule).
                entrySet().
                stream().
                sorted(rule).forEachOrdered(e -> {
            best.put(e.getKey(), e.getValue());
        });
        CSVUtil.writeToFileLexemeStatistic(best,
                                           arrHeaders,
                                           "best");
        writeToAnotherConsole(best.toString());
    }

    public static void writeToAnotherConsole(String text) throws IOException {
        Process p = Runtime.
                getRuntime().
                exec("cmd.exe /c start echo " +
                             "\"" +
                             text
                             + "\"");
    }

    public static String[] prepareHeaders(Collection<String> lexemes) {
        final List<String> headers = new ArrayList<>(lexemes.size() + 1);
        headers.addAll(lexemes);
        headers.sort(String::compareTo);
        headers.add(0, "seed");
        final String[] arrHeaders = new String[headers.size()];
        return headers.toArray(arrHeaders);
    }
}
