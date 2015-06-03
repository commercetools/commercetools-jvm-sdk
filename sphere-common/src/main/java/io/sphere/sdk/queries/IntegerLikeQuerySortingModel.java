package io.sphere.sdk.queries;

import java.util.Optional;

import static io.sphere.sdk.utils.ListUtils.listOf;

/**
 * 
 * @param <T> context type, like Channel
 * @param <V> argument type, Integer or Long
 */
abstract class IntegerLikeQuerySortingModel<T, V> extends QueryModelImpl<T>
        implements QuerySortingModel<T>, OptionalQueryModel<T>, EqualityQueryModel<T, V>,
        NotEqualQueryModel<T, V>, IsInQueryModel<T, V>, IsNotInQueryModel<T, V>, InequalityQueryModel<T, V> {
    protected IntegerLikeQuerySortingModel(final Optional<? extends QueryModel<T>> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    public QuerySort<T> sort(QuerySortDirection sortDirection) {
        return new SphereQuerySort<>(this, sortDirection);
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
    @SuppressWarnings("unchecked")
    public QueryPredicate<T> isIn(final V arg0, final V ... args) {
        return isInPredicate(listOf(arg0, args));
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
    @SuppressWarnings("unchecked")
    public QueryPredicate<T> isNotIn(final V arg0, final V ... args) {
        return isNotIn(listOf(arg0, args));
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