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

    public void addToStatistic(CrawlURL url) {
        statMap.computeIfAbsent(url, this::getLexemesStatistic);
    }

    public Map<String, Integer> getLexemesStatistic(CrawlURL url) {
        Map<String, Integer> map = new HashMap<>();
        lexemes.forEach(lexeme -> {
            map.put(lexeme,
                    getAmountOfEntries(url,
                                       lexeme));
        });
        return map;
    }

    public int getAmountOfEntries(CrawlURL url, String lexeme) {
        AtomicInteger amount = new AtomicInteger();
        Document htmlDoc = null;
        try {
            htmlDoc = url.tryGetDocument();
        } catch (IOException e) {
            return amount.get();
        }
        String text = htmlDoc.body().text();
        amount.set(getAmountOfOccurencesInString(text,
                                                 lexeme));
        return amount.get();
    }

    public int getAmountOfOccurencesInString(String text, String lexeme) {
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
                new HashMap<>(topURLbyTotalHints.size());
        topURLbyTotalHints.forEach(e -> {
            topURL.put(e, statMap.get(e));
        });

        return topURL;
    }
}
