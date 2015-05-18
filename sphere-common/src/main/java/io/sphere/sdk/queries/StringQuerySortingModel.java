package io.sphere.sdk.queries;

import java.util.List;
import java.util.Optional;

import static io.sphere.sdk.utils.IterableUtils.toStream;
import static io.sphere.sdk.utils.ListUtils.*;
import static java.util.stream.Collectors.toList;

public class StringQuerySortingModel<T> extends QueryModelImpl<T> implements QuerySortingModel<T>, StringQueryModel<T> {
    public StringQuerySortingModel(Optional<? extends QueryModel<T>> parent, String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public QuerySort<T> sort(QuerySortDirection sortDirection) {
        return new SphereQuerySort<>(this, sortDirection);
    }

    /**
     * Escapes Strings like that (Scala notation) """query by name " test name"""
     * @param s the unescaped String
     * @return the escaped string
     */
    public static String escape(final String s) {
        return s.replace("\"", "\\\"");
    }

    @Override
    public QueryPredicate<T> is(final String s) {
        return EqQueryPredicate.of(this, escape(s));
    }

    @Override
    public QueryPredicate<T> isNot(final String s) {
        return NotEqQueryPredicate.of(this, escape(s));
    }

    @Override
    public QueryPredicate<T> isOneOf(final String arg0, final String ... args) {
        return isOneOf(listOf(arg0, args));
    }

    @Override
    public QueryPredicate<T> isOneOf(final Iterable<String> args) {
        return new IsInQueryPredicate<>(this, escape(args));
    }

    @Override
    public QueryPredicate<T> isIn(final Iterable<String> args) {
        return isOneOf(args);
    }

    @Override
    public QueryPredicate<T> isGreaterThan(final String value) {
        return new IsGreaterThanQueryPredicate<>(this, value);
    }

    @Override
    public QueryPredicate<T> isLessThan(final String value) {
        return new IsLessThanQueryPredicate<>(this, value);
    }

    @Override
    public QueryPredicate<T> isLessThanOrEquals(final String value) {
        return new IsLessThanOrEqualsQueryPredicate<>(this, value);
    }

    @Override
    public QueryPredicate<T> isGreaterThanOrEqualTo(final String value) {
        return new IsGreaterThanOrEqualToQueryPredicate<>(this, value);
    }

    @Override
    public QueryPredicate<T> isNotIn(final Iterable<String> args) {
        return new IsNotInQueryPredicate<>(this, escape(args));
    }

    @Override
    public QueryPredicate<T> isNotIn(final String arg0, final String ... args) {
        return isNotIn(listOf(arg0, args));
    }

    @Override
    public QueryPredicate<T> isPresent() {
        return new OptionalQueryPredicate<>(this, true);
    }

    @Override
    public QueryPredicate<T> isNotPresent() {
        return new OptionalQueryPredicate<>(this, false);
    }

    private static List<String> escape(final Iterable<String> args) {
        return toStream(args).map(x -> escape(x)).collect(toList());
    }
}