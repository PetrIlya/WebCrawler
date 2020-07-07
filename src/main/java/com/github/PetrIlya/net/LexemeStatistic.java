package com.github.PetrIlya.net;

import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class LexemeStatistic {
    private final Set<String> lexemes;
    private final Map<CrawlURL, Map<String, Integer>> statMap;

    public LexemeStatistic(Set<String> lexemes) {
        this.lexemes = lexemes;
        this.statMap = new TreeMap<>();
    }

    /**
     * Add url to statistic
     *
     * @param url url to add
     */
    public void addToStatistic(CrawlURL url) {
        statMap.computeIfAbsent(url, this::getLexemesStatistic);
    }

    /**
     * Returns statistic for lexemes for given url
     *
     * @param url url to process
     * @return Map with statistic
     */
    public Map<String, Integer> getLexemesStatistic(CrawlURL url) {
        Map<String, Integer> map = new HashMap<>();
        lexemes.forEach(lexeme -> {
            map.put(lexeme,
                    getAmountOfEntries(url,
                                       lexeme));
        });
        return map;
    }

    /**
     * Amount of entries of given lexeme in url text
     *
     * @param url    url to process
     * @param lexeme lexeme to search
     * @return amount of entries
     */
    public int getAmountOfEntries(CrawlURL url, String lexeme) {
        AtomicInteger amount = new AtomicInteger();
        Document htmlDoc = null;
        try {
            htmlDoc = url.tryGetDocument();
        } catch (IOException e) {
            return amount.get();
        }
        String text = htmlDoc.body().text();
        amount.set(getAmountOfOccurrencesInString(text,
                                                  lexeme));
        return amount.get();
    }

    /**
     * @param text   Text to search lexeme
     * @param lexeme lexeme to search
     * @return amount of entries
     */
    public int getAmountOfOccurrencesInString(String text, String lexeme) {
        int amount = 0;
        int indexOfLexeme = 0;
        do {
            indexOfLexeme = text.indexOf(lexeme, indexOfLexeme);
            if (indexOfLexeme >= 0) {
                amount++;
                indexOfLexeme += lexeme.length() - 1;
            } else {
                return amount;
            }
        } while (true);
    }

    public Map<CrawlURL, Map<String, Integer>> getStatMap() {
        return statMap;
    }

    public Set<String> getLexemes() {
        return lexemes;
    }

    /**
     * Returns fixed amount of top urls by given rule
     *
     * @param limit limit of output
     * @param rule  rule to sort
     * @return Top elements
     */
    public Map<CrawlURL, Map<String, Integer>> getTopURLByRule(int limit,
                                                               Comparator<? super Map.
                                                                       Entry<CrawlURL,
                                                                       Map<String, Integer>>> rule) {
        if (limit < 0) {
            throw new IllegalArgumentException();
        }
        final List<CrawlURL> topURLbyTotalHints = statMap.
                entrySet().
                stream().
                sorted(rule).
                map(Map.Entry::getKey).
                limit(10).
                collect(Collectors.toList());
        Map<CrawlURL, Map<String, Integer>> topURL =
                new LinkedHashMap<>(topURLbyTotalHints.size());
        topURLbyTotalHints.forEach(e -> {
            topURL.put(e, statMap.get(e));
        });

        return topURL;
    }

    @Override
    public String toString() {
        final StringBuilder result = new StringBuilder();
        statMap.forEach((k, map) -> {
            result.append("Seed:").append(k.getUrl());
            map.entrySet().
                    stream().
                    sorted(Map.Entry.comparingByKey()).
                    forEachOrdered(e -> result.
                            append(" ").
                            append(e.getValue()));
            result.append("\n");
        });
        return result.toString();
    }
}
