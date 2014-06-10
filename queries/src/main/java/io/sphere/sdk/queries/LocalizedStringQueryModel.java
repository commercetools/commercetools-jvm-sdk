package io.sphere.sdk.queries;

import com.google.common.base.Optional;

import java.util.Locale;

public class LocalizedStringQueryModel<T> extends EmbeddedQueryModel<T, LocalizedStringQueryModel<T>> {
    public LocalizedStringQueryModel(Optional<? extends QueryModel<T>> parent, Optional<String> pathSegment) {
        super(parent, pathSegment);
    }

    public StringQueryWithSortingModel<T> lang(Locale locale) {
        return new StringQueryWithSortingModel<>(Optional.of(this), locale.toLanguageTag());
    }
}