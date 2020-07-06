package com.github.PetrIlya;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

public class LexemeConfig {
    public static void main(String[] args) throws IOException {
        writeLexemes(args);
    }

    public static void writeLexemes(String[] args) throws IOException {
        if (args.length == 0) {
            throw new IllegalArgumentException("Empty arguments");
        }
        if (args.length == 1) {
            throw new IllegalArgumentException("Lexemes doesn't specified");
        }
        String filePath = args[0];
        try (Writer writer = new PrintWriter(filePath)) {
            for (int i = 1; i < args.length; i++) {
                writer.write(args[i] + "\n");
            }
        }
    }
}
