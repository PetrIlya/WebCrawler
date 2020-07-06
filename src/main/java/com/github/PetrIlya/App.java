package com.github.PetrIlya;

import com.github.PetrIlya.crawler.LexemesCrawlerConfig;
import com.github.PetrIlya.crawler.LexemesWebCrawler;

public class App {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.exit(-1);
        }
        LexemesWebCrawler crawler = LexemesCrawlerConfig.
                getInstance(args[0]).
                getWebCrawler();
        crawler.startCrawl();
    }
}
