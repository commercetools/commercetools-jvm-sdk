package io.sphere.sdk.search;

import java.util.Locale;
import java.util.Optional;

public class LocalizedStringsSearchModel<T> extends SearchModelImpl<T> {
    public LocalizedStringsSearchModel(final Optional<? extends SearchModel<T>> parent, final Optional<String> pathSegment) {
        super(parent, pathSegment);
    }

    public StringSearchModel<T> lang(final Locale locale) {
        return new StringSearchModel<>(Optional.of(this), locale.toLanguageTag());
    }

    public static <T> LocalizedStringsSearchModel<T> of(final SearchModel<T> searchModel, final String pathSegment) {
        return new LocalizedStringsSearchModel<T>(Optional.of(searchModel), Optional.of(pathSegment));
    }
}