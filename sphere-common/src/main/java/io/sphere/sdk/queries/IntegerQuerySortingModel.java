package io.sphere.sdk.queries;

import java.util.Optional;

public class IntegerQuerySortingModel<T> extends IntegerLikeQuerySortingModel<T, Integer> {
    public IntegerQuerySortingModel(final Optional<? extends QueryModel<T>> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public Predicate<T> is(final Integer i) {
        return super.is(i);
    }

    @Override
    public Predicate<T> isGreaterThan(final Integer value) {
        return super.isGreaterThan(value);
    }

    @Override
    public Predicate<T> isGreaterThanOrEquals(final Integer value) {
        return super.isGreaterThanOrEquals(value);
    }

    @Override
    public Predicate<T> isIn(final Iterable<Integer> args) {
        return super.isIn(args);
    }

    @Override
    public Predicate<T> isLessThan(final Integer value) {
        return super.isLessThan(value);
    }

    @Override
    public Predicate<T> isLessThanOrEquals(final Integer value) {
        return super.isLessThanOrEquals(value);
    }

    @Override
    public Predicate<T> isNot(final Integer i) {
        return super.isNot(i);
    }

    @Override
    public Predicate<T> isNotIn(final Integer arg0, final Integer... args) {
        return super.isNotIn(arg0, args);
    }

    @Override
    public Predicate<T> isNotIn(final Iterable<Integer> args) {
        return super.isNotIn(args);
    }

    @Override
    public Predicate<T> isNotPresent() {
        return super.isNotPresent();
    }

    @Override
    public Predicate<T> isOneOf(final Integer arg0, final Integer... args) {
        return super.isOneOf(arg0, args);
    }

    @Override
    public Predicate<T> isOneOf(final Iterable<Integer> args) {
        return super.isOneOf(args);
    }

    @Override
    public Predicate<T> isPresent() {
        return super.isPresent();
    }

    @Override
    public Sort<T> sort(final SortDirection sortDirection) {
        return super.sort(sortDirection);
    }
}
