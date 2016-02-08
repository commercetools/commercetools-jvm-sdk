package io.sphere.sdk.queries;

public interface StringQueryModel<T> extends OptionalQueryModel<T>, EqualityQueryModel<T, String>,
        NotEqualQueryModel<T, String>, IsInQueryModel<T, String>, InequalityQueryModel<T, String>,
        IsNotInQueryModel<T, String> {
    @Override
    QueryPredicate<T> is(String s);

    @Override
    QueryPredicate<T> isNot(String s);

    @Override
    QueryPredicate<T> isIn(final Iterable<String> args);

    @Override
    QueryPredicate<T> isGreaterThan(final String value);

    @Override
    QueryPredicate<T> isLessThan(final String value);

    @Override
    QueryPredicate<T> isLessThanOrEqualTo(final String value);

    @Override
    QueryPredicate<T> isGreaterThanOrEqualTo(final String value);

    @Override
    QueryPredicate<T> isNotIn(final Iterable<String> args);

    @Override
    QueryPredicate<T> isPresent();

    @Override
    QueryPredicate<T> isNotPresent();
}
