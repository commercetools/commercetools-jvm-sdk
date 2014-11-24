package io.sphere.sdk.search;

import java.util.List;
import java.util.Optional;

import static io.sphere.sdk.utils.IterableUtils.toStream;
import static io.sphere.sdk.utils.ListUtils.listOf;
import static java.util.stream.Collectors.toList;

public class StringSearchModel<T> extends SearchModelImpl<T> {

    public StringSearchModel(Optional<? extends SearchModel<T>> parent, String pathSegment) {
        super(parent, pathSegment);
    }

    /**
     * Escapes Strings like that (Scala notation) """query by name " test name"""
     * @param s the unescaped String
     * @return the escaped string
     */
    public static String escape(final String s) {
        return s.replace("\"", "\\\"");
    }

    public FacetExpression<T> is(final String s) {
        return new EqFacetExpression<>(this, escape(s));
    }

    public FacetExpression<T> isOneOf(final String arg0, final String ... args) {
        return isOneOf(listOf(arg0, args));
    }

    public FacetExpression<T> isOneOf(final Iterable<String> args) {
        return new IsInFacetExpression<>(this, escape(args));
    }

     public FacetExpression<T> isIn(final Iterable<String> args) {
        return isOneOf(args);
    }

    public FacetExpression<T> isGreaterThan(final String value) {
        return new IsGreaterThanFacetExpression<>(this, value);
    }

    public FacetExpression<T> isLessThan(final String value) {
        return new IsLessThanFacetExpression<>(this, value);
    }

    public FacetExpression<T> isLessThanOrEqualsTo(final String value) {
        return new AtMostFacetExpression<>(this, value);
    }

    public FacetExpression<T> isGreaterThanOrEqualsTo(final String value) {
        return new AtLeastFacetExpression<>(this, value);
    }

    private static List<String> escape(final Iterable<String> args) {
        return toStream(args).map(x -> escape(x)).collect(toList());
    }
}