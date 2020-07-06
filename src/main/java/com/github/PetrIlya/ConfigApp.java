package com.github.PetrIlya;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;

public class ConfigApp {
    public static void main(String[] args) throws IOException {
        processArguments(args);
    }

    public static void processArguments(String[] args) throws IOException {
        Properties properties = new Properties(5);
        if (args.length <= 1) {
            throw new IllegalArgumentException("Invalid arguments");
        }
        properties.put("seed", args[0]);
        properties.put("lexemes", args[1]);
        if (args.length >= 3) {
            properties.put("max.pages", args[2]);
        }
        if (args.length >= 4) {
            properties.put("max.depth", args[3]);
        }
        String outFile = "properties.properties";
        if (args.length >= 5) {
            outFile = args[5];
        }
        try (PrintWriter writer = new PrintWriter(outFile)) {
            properties.store(writer, "Properties for WebCrawler start");
        }
    }
}
