package io.sphere.sdk.products.queries;

import io.sphere.sdk.queries.DirectionlessQuerySort;

public interface CategoryOrderHintsQueryModel<T> {
    DirectionlessQuerySort<T> category(final String categoryId);
}
