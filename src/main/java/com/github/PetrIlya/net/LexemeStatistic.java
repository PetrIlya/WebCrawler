package com.github.PetrIlya.net;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

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
        int amount = 0;
        final String document = url.getDocument().wholeText();
        if (document == null) {
            return 0;
        }
        int indexOfLexeme = 0;
        do {
            indexOfLexeme = document.indexOf(lexeme,
                                             indexOfLexeme + lexeme.length());
            if (indexOfLexeme > 0) {
                amount++;
            } else {
                return amount;
            }
        } while (true);
    }

    public Map<CrawlURL, Map<String, Integer>> getStatMap() {
        return statMap;
    }
}
