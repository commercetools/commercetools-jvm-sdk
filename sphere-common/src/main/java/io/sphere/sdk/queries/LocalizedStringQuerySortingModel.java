package io.sphere.sdk.queries;

import java.util.Locale;

public interface LocalizedStringQuerySortingModel<T> extends LocalizedStringQueryModel<T> {
    @Override
    StringQuerySortingModel<T> lang(Locale locale);

    @Override
    StringQuerySortingModel<T> locale(final Locale locale);
}
