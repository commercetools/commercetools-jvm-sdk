package io.sphere.sdk.queries;

import java.time.ZonedDateTime;

public interface TimestampModel<T> extends EqualityQueryModel<T, ZonedDateTime> ,
        NotEqualQueryModel<T, ZonedDateTime>, IsInQueryModel<T, ZonedDateTime>, InequalityQueryModel<T, ZonedDateTime>,
        IsNotInQueryModel<T, ZonedDateTime> {

    @Override
    QueryPredicate<T> is(final ZonedDateTime value);

    @Override
    QueryPredicate<T> isGreaterThan(final ZonedDateTime value);

    @Override
    QueryPredicate<T> isGreaterThanOrEqualTo(final ZonedDateTime value);

    @Override
    QueryPredicate<T> isLessThan(final ZonedDateTime value);

    @Override
    QueryPredicate<T> isLessThanOrEqualTo(final ZonedDateTime value);

    @Override
    QueryPredicate<T> isIn(final Iterable<ZonedDateTime> args);

    @Override
    QueryPredicate<T> isNotIn(final Iterable<ZonedDateTime> args);

    @Override
    QueryPredicate<T> isNot(final ZonedDateTime element);
}
