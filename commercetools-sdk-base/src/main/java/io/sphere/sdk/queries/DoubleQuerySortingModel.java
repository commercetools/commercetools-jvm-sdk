package io.sphere.sdk.queries;

public interface DoubleQuerySortingModel<T> extends QueryModel<T>, QuerySortingModel<T>, OptionalQueryModel<T>, EqualityQueryModel<T, Double>, NotEqualQueryModel<T, Double>, IsInQueryModel<T, Double>, IsNotInQueryModel<T, Double>, InequalityQueryModel<T, Double> {
    @Override
    QueryPredicate<T> is(Double i);

    @Override
    QueryPredicate<T> isGreaterThan(Double value);

    @Override
    QueryPredicate<T> isGreaterThanOrEqualTo(Double value);

    @Override
    QueryPredicate<T> isLessThan(Double value);

    @Override
    QueryPredicate<T> isLessThanOrEqualTo(Double value);

    @Override
    QueryPredicate<T> isNot(Double i);

    @Override
    QueryPredicate<T> isNotIn(Iterable<Double> args);

    @Override
    QueryPredicate<T> isNotPresent();

    @Override
    QueryPredicate<T> isIn(Iterable<Double> args);

    @Override
    QueryPredicate<T> isPresent();
}
