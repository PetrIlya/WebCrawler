package com.github.PetrIlya.crawler;

import com.github.PetrIlya.util.PathUtil;
import com.github.PetrIlya.util.PropertiesUtil;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

public class LexemesCrawlerConfig extends CrawlerConfig {

    private int maxPagesVisited;
    private int maxDepth;
    private String seedAddr;
    private Set<String> lexemesToSearch;

    private LexemesCrawlerConfig() {
    }

    public LexemesCrawlerConfig(int maxPagesVisited,
                                int maxDepth,
                                String seedAddr,
                                Set<String> lexemesToSearch) {
        this.maxPagesVisited = maxPagesVisited;
        this.maxDepth = maxDepth;
        Objects.requireNonNull(seedAddr);
        this.seedAddr = seedAddr;
        Objects.requireNonNull(lexemesToSearch);
        this.lexemesToSearch = lexemesToSearch;
    }

    /**
     * Initializes WebCrawlerConfig with given config
     *
     * @param pathToProperties path to configure file
     * @return LexemesCrawlerConfig instance
     */
    public static LexemesCrawlerConfig getInstance(final String pathToProperties) {
        try {
            Properties properties = PropertiesUtil.
                    getProperties(DEFAULT_VARIABLES_AMOUNT,
                                  PathUtil.getAbsolutePathToResource(pathToProperties));
            LexemesCrawlerConfig config = new LexemesCrawlerConfig();
            config.processProperties(properties);
            return config;
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    /**
     * Build WebCrawler according to the config
     *
     * @return WebCrawler instance
     */
    @Override
    public LexemesWebCrawler getWebCrawler() {
        return new LexemesWebCrawler(this.maxDepth,
                                     this.maxPagesVisited,
                                     this.seedAddr,
                                     this.lexemesToSearch);
    }

    /**
     * Processes properties and sets config variables
     *
     * @param properties Properties to process
     */
    private void processProperties(final Properties properties) {
        final String maxPagesVisited = properties.getProperty("max.pages",
                                                              Integer.toString(DEFAULT_MAX_PAGES_VISITED));
        final String maxDepth = properties.getProperty("max.depth",
                                                       Integer.toString(DEFAULT_MAX_DEPTH));
        final String seedAddr = properties.getProperty("seed", "");
        final String pathToLexemesFile = properties.getProperty("lexemes", "");
        final Set<String> lexemes = getLexemesFromFile(pathToLexemesFile);
        setProperties(maxPagesVisited,
                      maxDepth,
                      seedAddr,
                      lexemes);
    }

    /**
     * Returns set of lexemes from a file for crawler to search
     *
     * @param pathToLexemesFile path to a file to get lexemes
     * @return Set of lexemes to process
     */
    private Set<String> getLexemesFromFile(final String pathToLexemesFile) {
        try {
            Set<String> lexemes = Files.
                    lines(Paths.
                            get(PathUtil.
                                    getAbsolutePathToResource(pathToLexemesFile))).
                    filter(lexeme -> !lexeme.isEmpty()).
                    collect(Collectors.toSet());
            if (lexemes.size() == 0) {
                throw new IllegalArgumentException("Lexemes file is empty");
            }
            return lexemes;
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * Sets properties to fields
     *
     * @param maxPagesVisited limit for pages
     * @param maxDepth        limit for depth
     * @param seedAddr        seed to start
     * @param lexemes         lexemes to search
     */
    private void setProperties(final String maxPagesVisited,
                               final String maxDepth,
                               final String seedAddr,
                               final Set<String> lexemes) {
        try {
            this.maxPagesVisited = Integer.parseInt(maxPagesVisited);
            this.maxDepth = Integer.parseInt(maxDepth);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(e);
        }
        this.seedAddr = seedAddr;
        this.lexemesToSearch = lexemes;
    }

    public int getMaxPagesVisited() {
        return maxPagesVisited;
    }

    public int getMaxDepth() {
        return maxDepth;
    }

    public String getSeedAddr() {
        return seedAddr;
    }

    public Set<String> getLexemesToSearch() {
        return lexemesToSearch;
    }
}
