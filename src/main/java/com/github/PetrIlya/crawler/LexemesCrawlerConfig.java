package com.github.PetrIlya.crawler;

import com.github.PetrIlya.util.PathUtil;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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

    public static LexemesCrawlerConfig getInstance(final String pathToProperties) {
        if (pathToProperties.isEmpty()) {
            throw new IllegalArgumentException("Path to the configuration file doesn't specified");
        }
        final LexemesCrawlerConfig lexemesCrawlerConfig = new LexemesCrawlerConfig();
        final Properties properties = new Properties(DEFAULT_VARIABLES_AMOUNT);
        try {
            final String pathToResource = PathUtil.getAbsolutePathToResource(pathToProperties);
            final InputStream propertiesStream = new FileInputStream(pathToResource);
            properties.load(propertiesStream);
            lexemesCrawlerConfig.processProperties(properties);
            propertiesStream.close();
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
        return lexemesCrawlerConfig;
    }

    @Override
    public LexemesWebCrawler getWebCrawler() {
        return null;
    }

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
