package io.sphere.sdk.queries;

import javax.annotation.Nullable;

/**
 * 
 * @param <T> context type, like Channel
 * @param <V> argument type, Integer or Long
 */
abstract class NumberLikeQuerySortingModelImpl<T, V> extends QueryModelImpl<T>
        implements QuerySortingModel<T>, OptionalQueryModel<T>, EqualityQueryModel<T, V>,
        NotEqualQueryModel<T, V>, IsInQueryModel<T, V>, IsNotInQueryModel<T, V>, InequalityQueryModel<T, V> {
    protected NumberLikeQuerySortingModelImpl(@Nullable final QueryModel<T> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public DirectionlessQuerySort<T> sort() {
        return new DirectionlessQuerySort<>(this);
    }

    @Override
    public QueryPredicate<T> is(final V i) {
        return isPredicate(i);
    }

    @Override
    public QueryPredicate<T> isNot(final V i) {
        return isNotPredicate(i);
    }

    @Override
    public QueryPredicate<T> isIn(final Iterable<V> args) {
        return isInPredicate(args);
    }

    @Override
    public QueryPredicate<T> isGreaterThan(final V value) {
        return isGreaterThanPredicate(value);
    }

    @Override
    public QueryPredicate<T> isLessThan(final V value) {
        return isLessThanPredicate(value);
    }

    @Override
    public QueryPredicate<T> isLessThanOrEqualTo(final V value) {
        return isLessThanOrEqualToPredicate(value);
    }

    @Override
    public QueryPredicate<T> isGreaterThanOrEqualTo(final V value) {
        return isGreaterThanOrEqualToPredicate(value);
    }

    @Override
    public QueryPredicate<T> isNotIn(final Iterable<V> args) {
        return isNotInPredicate(args);
    }

    @Override
    public QueryPredicate<T> isPresent() {
        return isPresentPredicate();
    }

    @Override
    public QueryPredicate<T> isNotPresent() {
        return isNotPresentPredicate();
    }
}