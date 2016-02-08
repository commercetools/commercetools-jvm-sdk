package io.sphere.sdk.queries;

import java.util.Locale;

public interface LocalizedStringOptionalQueryModel<T> extends LocalizedStringQueryModel<T>, OptionalQueryModel<T> {

    @Override
    StringQueryModel<T> lang(final Locale locale);

    @Override
    StringQueryModel<T> locale(final Locale locale);

    @Override
    QueryPredicate<T> isNotPresent();

    @Override
    QueryPredicate<T> isPresent();
}
