package io.sphere.sdk.products.search;

import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.search.model.SearchModel;
import io.sphere.sdk.search.model.SearchModelImpl;
import io.sphere.sdk.search.model.TermFilterSearchModel;

import javax.annotation.Nullable;

final class ProductVariantAvailabilityFilterSearchModelImpl extends SearchModelImpl<ProductProjection> implements ProductVariantAvailabilityFilterSearchModel {

    ProductVariantAvailabilityFilterSearchModelImpl(@Nullable final SearchModel<ProductProjection> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public TermFilterSearchModel<ProductProjection, Boolean> isOnStock() {
        return booleanSearchModel("isOnStock").filtered();
    }
}
