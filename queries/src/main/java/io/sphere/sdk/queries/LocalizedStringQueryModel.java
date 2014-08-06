package io.sphere.sdk.queries;

import java.util.Optional;

import java.util.Locale;

public class LocalizedStringQueryModel<T> extends EmbeddedQueryModel<T> {
    public LocalizedStringQueryModel(Optional<? extends QueryModel<T>> parent, Optional<String> pathSegment) {
        super(parent, pathSegment);
    }

    public StringQuerySortingModel<T> lang(Locale locale) {
        return new StringQuerySortingModel<>(Optional.of(this), locale.toLanguageTag());
    }
}
