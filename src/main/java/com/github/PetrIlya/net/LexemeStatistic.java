package com.github.PetrIlya.net;

import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;

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
}
