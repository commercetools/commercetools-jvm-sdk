package io.sphere.sdk.queries;

import java.util.Locale;

public interface LocalizedStringQueryModel<T> {
    StringQueryModel<T> lang(final Locale locale);
}
