package io.sphere.sdk.queries;

public interface IntegerQuerySortingModel<T> extends QueryModel<T>, QuerySortingModel<T>, IntegerQueryModel<T> {
    @Override
    QueryPredicate<T> is(Integer i);

    @Override
    QueryPredicate<T> isGreaterThan(Integer value);

    @Override
    QueryPredicate<T> isGreaterThanOrEqualTo(Integer value);

    @Override
    QueryPredicate<T> isLessThan(Integer value);

    @Override
    QueryPredicate<T> isLessThanOrEqualTo(Integer value);

    @Override
    QueryPredicate<T> isNot(Integer i);

    @Override
    QueryPredicate<T> isNotIn(Iterable<Integer> args);

    @Override
    QueryPredicate<T> isNotPresent();

    @Override
    QueryPredicate<T> isIn(Iterable<Integer> args);

    @Override
    QueryPredicate<T> isPresent();
}
