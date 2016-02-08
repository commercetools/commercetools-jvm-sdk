package io.sphere.sdk.queries;

public interface LongQueryModel<T> extends QueryModel<T>, OptionalQueryModel<T>, EqualityQueryModel<T, Long>, NotEqualQueryModel<T, Long>, IsInQueryModel<T, Long>, IsNotInQueryModel<T, Long>, InequalityQueryModel<T, Long> {
    @Override
    QueryPredicate<T> is(Long i);

    @Override
    QueryPredicate<T> isGreaterThan(Long value);

    @Override
    QueryPredicate<T> isGreaterThanOrEqualTo(Long value);

    @Override
    QueryPredicate<T> isLessThan(Long value);

    @Override
    QueryPredicate<T> isLessThanOrEqualTo(Long value);

    @Override
    QueryPredicate<T> isNot(Long i);

    @Override
    QueryPredicate<T> isNotIn(Iterable<Long> args);

    @Override
    QueryPredicate<T> isNotPresent();

    @Override
    QueryPredicate<T> isIn(Iterable<Long> args);

    @Override
    QueryPredicate<T> isPresent();
}
