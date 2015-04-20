package io.sphere.sdk.queries;

import java.util.Optional;

public class LongQuerySortingModel<T> extends IntegerLikeQuerySortingModel<T, Long> {
    public LongQuerySortingModel(final Optional<? extends QueryModel<T>> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public Predicate<T> is(final Long i) {
        return super.is(i);
    }

    @Override
    public Predicate<T> isGreaterThan(final Long value) {
        return super.isGreaterThan(value);
    }

    @Override
    public Predicate<T> isGreaterThanOrEquals(final Long value) {
        return super.isGreaterThanOrEquals(value);
    }

    @Override
    public Predicate<T> isIn(final Iterable<Long> args) {
        return super.isIn(args);
    }

    @Override
    public Predicate<T> isLessThan(final Long value) {
        return super.isLessThan(value);
    }

    @Override
    public Predicate<T> isLessThanOrEquals(final Long value) {
        return super.isLessThanOrEquals(value);
    }

    @Override
    public Predicate<T> isNot(final Long i) {
        return super.isNot(i);
    }

    @Override
    public Predicate<T> isNotIn(final Long arg0, final Long... args) {
        return super.isNotIn(arg0, args);
    }

    @Override
    public Predicate<T> isNotIn(final Iterable<Long> args) {
        return super.isNotIn(args);
    }

    @Override
    public Predicate<T> isNotPresent() {
        return super.isNotPresent();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Predicate<T> isOneOf(final Long arg0, final Long... args) {
        return super.isOneOf(arg0, args);
    }

    @Override
    public Predicate<T> isOneOf(final Iterable<Long> args) {
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
