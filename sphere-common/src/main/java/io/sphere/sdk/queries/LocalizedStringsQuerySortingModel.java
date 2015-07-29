package io.sphere.sdk.queries;

import java.util.Locale;

public interface LocalizedStringsQuerySortingModel<T> extends LocalizedStringsQueryModel<T> {
    @Override
    StringQuerySortingModel<T> lang(Locale locale);
}
