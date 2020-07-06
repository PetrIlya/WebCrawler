package com.github.PetrIlya.net;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class CrawlURL implements Comparable<CrawlURL> {
    private final String url;
    private int depth;
    private Document document;

    public CrawlURL(String url, int depth) {
        this.url = url;
        this.depth = depth;
    }

    public String getUrl() {
        return url;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public Document getDocument() {
        return document;
    }

    public Document tryGetDocument() throws IOException {
        if (document == null) {
            document = Jsoup.
                    connect(url).
                    get();
            return document;
        }
        return document;
    }

    public Set<CrawlURL> getOutgoingURLs() throws IOException {
        if (document == null) {
            document = Jsoup.connect(url).get();
        }
        return document.
                select("a[href]").
                stream().
                map(e -> new CrawlURL(e.attr("abs:href"),
                                      depth + 1)).
                collect(Collectors.toSet());
    }

    @Override
    public int compareTo(CrawlURL o) {
        return o.getUrl().compareTo(this.getUrl());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CrawlURL crawlURL = (CrawlURL) o;
        return url.equals(crawlURL.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url);
    }
}
