package io.sphere.sdk.queries;

import java.util.Optional;

import static io.sphere.sdk.utils.ListUtils.listOf;

/**
 * 
 * @param <T> context type, like Channel
 * @param <V> argument type, Integer or Long
 */
public abstract class IntegerLikeQuerySortingModel<T, V> extends QueryModelImpl<T> implements QuerySortingModel<T> {
    protected IntegerLikeQuerySortingModel(final Optional<? extends QueryModel<T>> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    public QuerySort<T> sort(QuerySortDirection sortDirection) {
        return new SphereQuerySort<>(this, sortDirection);
    }

    public QueryPredicate<T> is(final V i) {
        return EqQueryPredicate.of(this, i);
    }

    public QueryPredicate<T> isNot(final V i) {
        return NotEqQueryPredicate.of(this, i);
    }

    @SuppressWarnings("unchecked")
    public QueryPredicate<T> isOneOf(final V arg0, final V ... args) {
        return isOneOf(listOf(arg0, args));
    }

    public QueryPredicate<T> isOneOf(final Iterable<V> args) {
        return new IsInQueryPredicate<>(this, args);
    }

    public QueryPredicate<T> isIn(final Iterable<V> args) {
        return isOneOf(args);
    }

    public QueryPredicate<T> isGreaterThan(final V value) {
        return new IsGreaterThanQueryPredicate<>(this, value);
    }

    public QueryPredicate<T> isLessThan(final V value) {
        return new IsLessThanQueryPredicate<>(this, value);
    }

    public QueryPredicate<T> isLessThanOrEquals(final V value) {
        return new IsLessThanOrEqualsQueryPredicate<>(this, value);
    }

    public QueryPredicate<T> isGreaterThanOrEquals(final V value) {
        return new IsGreaterThanOrEqualsQueryPredicate<>(this, value);
    }

    public QueryPredicate<T> isNotIn(final Iterable<V> args) {
        return new IsNotInQueryPredicate<>(this, args);
    }

    @SuppressWarnings("unchecked")
    public QueryPredicate<T> isNotIn(final V arg0, final V ... args) {
        return isNotIn(listOf(arg0, args));
    }

    public QueryPredicate<T> isPresent() {
        return new OptionalQueryPredicate<>(this, true);
    }

    public QueryPredicate<T> isNotPresent() {
        return new OptionalQueryPredicate<>(this, false);
    }
}