package io.sphere.sdk.search;

import java.util.Optional;

public class StringSearchModel<T> extends SearchModelImpl<T> implements TermModel<T, String>, SearchSortingModel<T> {

    public StringSearchModel(final Optional<? extends SearchModel<T>> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public TermFilterSearchModel<T, String> filter() {
        return new TermFilterSearchModel<>(Optional.of(this), Optional.empty(), this::render);
    }

    @Override
    public TermFacetSearchModel<T, String> facet() {
        return new TermFacetSearchModel<>(Optional.of(this), Optional.empty(), this::render);
    }

    @Override
    public SearchSort<T> sort(SearchSortDirection sortDirection) {
        return new SphereSearchSort<>(this, sortDirection);
    }

    private String render(final String value) {
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