package io.sphere.sdk.search.model;

import javax.annotation.Nullable;
import java.util.Locale;

public class LocalizedStringFilterSearchModel<T> extends SearchModelImpl<T> {

    public LocalizedStringFilterSearchModel(@Nullable final SearchModel<T> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment);
    }

    public TermFilterSearchModel<T, String> locale(final Locale locale) {
        return new StringSearchModel<>(this, locale.toLanguageTag()).filtered();
    }
}