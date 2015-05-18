package io.sphere.sdk.queries;

import java.util.Optional;

public class IntegerQuerySortingModel<T> extends IntegerLikeQuerySortingModel<T, Integer> {
    public IntegerQuerySortingModel(final Optional<? extends QueryModel<T>> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public QueryPredicate<T> is(final Integer i) {
        return super.is(i);
    }

    @Override
    public QueryPredicate<T> isGreaterThan(final Integer value) {
        return super.isGreaterThan(value);
    }

    @Override
    public QueryPredicate<T> isGreaterThanOrEqualTo(final Integer value) {
        return super.isGreaterThanOrEqualTo(value);
    }

    @Override
    public QueryPredicate<T> isLessThan(final Integer value) {
        return super.isLessThan(value);
    }

    @Override
    public QueryPredicate<T> isLessThanOrEquals(final Integer value) {
        return super.isLessThanOrEquals(value);
    }

    @Override
    public QueryPredicate<T> isNot(final Integer i) {
        return super.isNot(i);
    }

    @Override
    public QueryPredicate<T> isNotIn(final Integer arg0, final Integer... args) {
        return super.isNotIn(arg0, args);
    }

    @Override
    public QueryPredicate<T> isNotIn(final Iterable<Integer> args) {
        return super.isNotIn(args);
    }

    @Override
    public QueryPredicate<T> isNotPresent() {
        return super.isNotPresent();
    }

    @Override
    public QueryPredicate<T> isIn(final Integer arg0, final Integer... args) {
        return super.isIn(arg0, args);
    }

    @Override
    public QueryPredicate<T> isIn(final Iterable<Integer> args) {
        return super.isIn(args);
    }

    @Override
    public QueryPredicate<T> isPresent() {
        return super.isPresent();
    }

    @Override
    public QuerySort<T> sort(final QuerySortDirection sortDirection) {
        return super.sort(sortDirection);
    }
}
