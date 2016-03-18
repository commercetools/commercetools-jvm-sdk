package io.sphere.sdk.search.model;

import io.sphere.sdk.search.FilterExpression;

import javax.annotation.Nullable;
import java.util.List;

public final class EnumFilterSearchModel<T> extends SearchModelImpl<T> implements ExistsAndMissingFilterSearchModelSupport<T> {

    EnumFilterSearchModel(@Nullable final SearchModel<T> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment);
    }

    public TermFilterSearchModel<T, String> key() {
        return new StringSearchModel<>(this, "key").filtered();
    }

    public TermFilterSearchModel<T, String> label() {
        return new StringSearchModel<>(this, "label").filtered();
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
