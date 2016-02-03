package io.sphere.sdk.search.model;

import javax.annotation.Nullable;

public final class MoneySortSearchModel<T, S extends SortSearchModel<T>> extends SortableSearchModel<T, S> {

    MoneySortSearchModel(@Nullable final SearchModel<T> parent, @Nullable final String pathSegment, final SearchSortFactory<T, S> searchSortFactory) {
        super(parent, pathSegment, searchSortFactory);
    }

    public S centAmount() {
        return sorted("centAmount");
    }

    public S currency() {
        return sorted("currencyCode");
    }
}
