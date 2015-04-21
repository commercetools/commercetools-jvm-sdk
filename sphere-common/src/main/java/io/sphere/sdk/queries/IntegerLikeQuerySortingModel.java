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

    public Predicate<T> is(final V i) {
        return EqPredicate.of(this, i);
    }

    public Predicate<T> isNot(final V i) {
        return NotEqPredicate.of(this, i);
    }

    @SuppressWarnings("unchecked")
    public Predicate<T> isOneOf(final V arg0, final V ... args) {
        return isOneOf(listOf(arg0, args));
    }

    public Predicate<T> isOneOf(final Iterable<V> args) {
        return new IsInPredicate<>(this, args);
    }

    public Predicate<T> isIn(final Iterable<V> args) {
        return isOneOf(args);
    }

    public Predicate<T> isGreaterThan(final V value) {
        return new IsGreaterThanPredicate<>(this, value);
    }

    public Predicate<T> isLessThan(final V value) {
        return new IsLessThanPredicate<>(this, value);
    }

    public Predicate<T> isLessThanOrEquals(final V value) {
        return new IsLessThanOrEqualsPredicate<>(this, value);
    }

    public Predicate<T> isGreaterThanOrEquals(final V value) {
        return new IsGreaterThanOrEqualsPredicate<>(this, value);
    }

    public Predicate<T> isNotIn(final Iterable<V> args) {
        return new IsNotInPredicate<>(this, args);
    }

    @SuppressWarnings("unchecked")
    public Predicate<T> isNotIn(final V arg0, final V ... args) {
        return isNotIn(listOf(arg0, args));
    }

    public Predicate<T> isPresent() {
        return new OptionalPredicate<>(this, true);
    }

    public Predicate<T> isNotPresent() {
        return new OptionalPredicate<>(this, false);
    }
}