package io.sphere.sdk.search;

import java.util.Optional;

public class StringSearchModel<T> extends TermSearchModel<T, String> {

    public StringSearchModel(final Optional<? extends SearchModel<T>> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    public StringSearchModel(final Optional<? extends SearchModel<T>> parent, final Optional<String> pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    protected String render(final String value) {
        return "\"" + escape(value) + "\"";
    }

    /**
     * Escapes Strings like that (Scala notation) """query by name " test name"""
     * @param value the unescaped string.
     * @return the escaped string
     */
    private static String escape(final String value) {
        return value.replace("\"", "\\\"");
    }
}