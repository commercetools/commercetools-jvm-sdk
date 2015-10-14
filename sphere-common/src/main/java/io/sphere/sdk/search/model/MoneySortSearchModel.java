package io.sphere.sdk.search.model;

import javax.annotation.Nullable;

public class MoneySortSearchModel<T, S extends SortSearchModel<T>> extends SortableSearchModel<T, S> {

    public MoneySortSearchModel(@Nullable final SearchModel<T> parent, @Nullable final String pathSegment,
                                final SearchSortBuilder<T, S> searchSortBuilder) {
        super(parent, pathSegment, searchSortBuilder);
    }

    public S centAmount() {
        return sorted("centAmount");
    }

    public S currency() {
        return sorted("currencyCode");
    }
}
