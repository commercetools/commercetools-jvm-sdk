package io.sphere.sdk.search.model;

import io.sphere.sdk.search.FilterExpression;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Locale;

public final class LocalizedStringFilterSearchModel<T> extends SearchModelImpl<T> implements ExistsAndMissingFilterSearchModelSupport<T> {

    LocalizedStringFilterSearchModel(@Nullable final SearchModel<T> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment);
    }

    public TermFilterExistsAndMissingSearchModel<T, String> locale(final Locale locale) {
        return new StringSearchModel<>(this, locale.toLanguageTag()).filtered();
    }

    @Override
    public List<FilterExpression<T>> exists() {
        return existsFilters();
    }

    @Override
    public List<FilterExpression<T>> missing() {
        return missingFilters();
    }
}