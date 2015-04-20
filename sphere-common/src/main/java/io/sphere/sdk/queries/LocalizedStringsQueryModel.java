package io.sphere.sdk.queries;

import java.util.Locale;

public interface LocalizedStringsQueryModel<T> extends QueryModel<T> {
    StringQueryModel<T> lang(final Locale locale);
}
