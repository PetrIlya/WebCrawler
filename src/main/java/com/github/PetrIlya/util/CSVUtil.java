package com.github.PetrIlya.util;

import com.github.PetrIlya.net.CrawlURL;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.FileWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class CSVUtil {
    /**
     * Assumes that headers and map keys have the same order
     *
     * @param statistic  statistic to get the map
     * @param headers    headers for CSV file
     * @param pathToFile path to store records
     * @throws IOException if something went wrong
     */
    public static void writeToFileLexemeStatistic(Map<CrawlURL, Map<String, Integer>> statistic,
                                                  String[] headers,
                                                  String pathToFile) throws IOException {
        final int amountOfColumns = headers.length;
        try (CSVPrinter printer = new CSVPrinter(new FileWriter(pathToFile),
                                                 CSVFormat.DEFAULT.
                                                         withHeader(headers))) {
            statistic.forEach((seed, lexemeMap) -> {
                try {
                    printer.printRecord(getRecordsToWrite(amountOfColumns,
                                                          seed,
                                                          lexemeMap));
                } catch (IOException e) {
                    throw new UncheckedIOException(e);
                }
            });
        }
    }

    private static List<String> getRecordsToWrite(int amountOfColumns,
                                                  CrawlURL seed,
                                                  Map<String, Integer> lexemeMap) {
        final List<String> orderedList = new ArrayList<>(amountOfColumns);
        orderedList.add(seed.getUrl());
        lexemeMap.
                entrySet().
                stream().
                sorted(Map.Entry.comparingByKey()).
                forEachOrdered(e -> orderedList.
                        add(Integer.
                                toString(e.getValue())
                        )
                );
        return orderedList;
    }
}
