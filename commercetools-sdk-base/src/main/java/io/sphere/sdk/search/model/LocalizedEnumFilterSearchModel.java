package io.sphere.sdk.search.model;

import io.sphere.sdk.search.FilterExpression;

import javax.annotation.Nullable;
import java.util.List;

public final class LocalizedEnumFilterSearchModel<T> extends SearchModelImpl<T> implements ExistsAndMissingFilterSearchModelSupport<T> {

    LocalizedEnumFilterSearchModel(@Nullable final SearchModel<T> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment);
    }

    public TermFilterSearchModel<T, String> key() {
        return new StringSearchModel<>(this, "key").filtered();
    }

    public LocalizedStringFilterSearchModel<T> label() {
        return new LocalizedStringFilterSearchModel<>(this, "label");
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
