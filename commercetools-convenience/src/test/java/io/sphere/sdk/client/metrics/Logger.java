package io.sphere.sdk.client.metrics;

import java.util.LinkedList;
import java.util.List;

import static java.util.stream.Collectors.joining;

public class Logger {
    private static final List<String> statements = new LinkedList<>();

    public static void trace(final String s) {
        statements.add(s);
    }

    public static String getAndClear() {
        final String res = statements.stream().collect(joining("\n"));
        statements.clear();
        return res;
    }
}
