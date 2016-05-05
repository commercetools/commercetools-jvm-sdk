package io.sphere.sdk.queries;

import javax.annotation.Nullable;
import java.util.List;

import static io.sphere.sdk.utils.SphereInternalUtils.toStream;
import static java.util.stream.Collectors.toList;

final class StringQuerySortingModelImpl<T> extends QueryModelImpl<T> implements StringQuerySortingModel<T> {
    public StringQuerySortingModelImpl(@Nullable final QueryModel<T> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public DirectionlessQuerySort<T> sort() {
        return new DirectionlessQuerySort<>(this);
    }

    /**
     * Escapes Strings like that (Scala notation) """query by name " test name"""
     * @param s the unescaped String
     * @return the escaped string
     */
    private static String escape(final String s) {
        return StringQuerySortingModel.escape(s);
    }

    private static String normalize(final String s) {
        return StringQuerySortingModel.normalize(s);
    }

    @Override
    public QueryPredicate<T> is(final String s) {
        return isPredicate(s);
    }

    @Override
    public QueryPredicate<T> isNot(final String s) {
        return isNotPredicate(s);
    }

    @Override
    public QueryPredicate<T> isIn(final Iterable<String> args) {
        return new IsInQueryPredicate<>(this, normalize(args));
    }

    @Override
    public QueryPredicate<T> isGreaterThan(final String s) {
        return ComparisonQueryPredicate.ofIsGreaterThan(this, normalize(s));
    }

    @Override
    public QueryPredicate<T> isLessThan(final String s) {
        return ComparisonQueryPredicate.ofIsLessThan(this, normalize(s));
    }

    @Override
    public QueryPredicate<T> isLessThanOrEqualTo(final String s) {
        return ComparisonQueryPredicate.ofIsLessThanOrEqualTo(this, normalize(s));
    }

    @Override
    public QueryPredicate<T> isGreaterThanOrEqualTo(final String s) {
        return ComparisonQueryPredicate.ofGreaterThanOrEqualTo(this, normalize(s));
    }

    @Override
    public QueryPredicate<T> isNotIn(final Iterable<String> args) {
        return new IsNotInQueryPredicate<>(this, normalize(args));
    }

    @Override
    public QueryPredicate<T> isPresent() {
        return isPresentPredicate();
    }

    @Override
    public QueryPredicate<T> isNotPresent() {
        return isNotPresentPredicate();
    }

    private static List<String> normalize(final Iterable<String> args) {
        return toStream(args).map(x -> normalize(x)).collect(toList());
    }

    private static List<String> escape(final Iterable<String> args) {
        return toStream(args).map(x -> escape(x)).collect(toList());
    }
}