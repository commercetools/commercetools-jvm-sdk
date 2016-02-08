package io.sphere.sdk.products.queries;

import io.sphere.sdk.queries.DirectionlessQuerySort;
import io.sphere.sdk.queries.QueryModel;
import io.sphere.sdk.queries.QueryModelImpl;

final class CategoryOrderHintsQueryModelImpl<T> extends QueryModelImpl<T> implements CategoryOrderHintsQueryModel<T> {
    public CategoryOrderHintsQueryModelImpl(final QueryModel<T> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public DirectionlessQuerySort<T> category(final String categoryId) {
        return DirectionlessQuerySort.of(new QueryModelImpl<T>(this, categoryId){});
    }
}
