package io.sphere.sdk.queries;

import java.util.Locale;

public interface LocalizedStringQueryModel<T> extends QueryModel<T> {
    /**
     * Alias for {@link #locale(Locale)}.
     *
     * @param locale the locale to query for
     * @return string model for the specified locale
     */
    StringQueryModel<T> lang(final Locale locale);

    StringQueryModel<T> locale(final Locale locale);
}
