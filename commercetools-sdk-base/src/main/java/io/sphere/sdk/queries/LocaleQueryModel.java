package io.sphere.sdk.queries;

import java.util.Locale;

public interface LocaleQueryModel<T> extends EqualityQueryModel<T, Locale>, NotEqualQueryModel<T, Locale>, IsInQueryModel<T, Locale> {

    @Override
    QueryPredicate<T> is(final Locale value);

    @Override
    QueryPredicate<T> isIn(final Iterable<Locale> args);

    @Override
    QueryPredicate<T> isNot(final Locale element);
}
