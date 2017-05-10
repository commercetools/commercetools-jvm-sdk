package io.sphere.sdk.queries;

import java.math.BigDecimal;

public interface BigDecimalQuerySortingModel<T> extends QueryModel<T>, QuerySortingModel<T>, OptionalQueryModel<T>, EqualityQueryModel<T, BigDecimal>, NotEqualQueryModel<T, BigDecimal>, IsInQueryModel<T, BigDecimal>, IsNotInQueryModel<T, BigDecimal>, InequalityQueryModel<T, BigDecimal>, BigDecimalQueryModel<T> {
    @Override
    QueryPredicate<T> is(BigDecimal i);

    @Override
    QueryPredicate<T> isGreaterThan(BigDecimal value);

    @Override
    QueryPredicate<T> isGreaterThanOrEqualTo(BigDecimal value);

    @Override
    QueryPredicate<T> isLessThan(BigDecimal value);

    @Override
    QueryPredicate<T> isLessThanOrEqualTo(BigDecimal value);

    @Override
    QueryPredicate<T> isNot(BigDecimal i);

    @Override
    QueryPredicate<T> isNotIn(Iterable<BigDecimal> args);

    @Override
    QueryPredicate<T> isNotPresent();

    @Override
    QueryPredicate<T> isIn(Iterable<BigDecimal> args);

    @Override
    QueryPredicate<T> isPresent();
}
