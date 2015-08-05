package io.sphere.sdk.search;

import javax.annotation.Nullable;
import java.util.Locale;

public class LocalizedStringSearchModel<T, S extends SearchSortDirection> extends SearchModelImpl<T> {

    public LocalizedStringSearchModel(@Nullable final SearchModel<T> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    public StringSearchModel<T, S> locale(final Locale locale) {
        return new StringSearchModel<>(this, locale.toLanguageTag());
    }
}