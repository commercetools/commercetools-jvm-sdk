package io.sphere.sdk.products.search;

import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.search.*;

import javax.annotation.Nullable;

public class ProductVariantSearchModel extends SearchModelImpl<ProductProjection> {

    ProductVariantSearchModel(@Nullable final SearchModel<ProductProjection> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment);
    }

    public ProductAttributeSearchModel attribute() {
        return new ProductAttributeSearchModel(this, "attributes");
    }

    public MoneySearchModel<ProductProjection, DirectionlessSearchSortModel<ProductProjection>> price() {
        return new MoneySearchModel<>(this, "price", new DirectionlessSearchSortBuilder<>());
    }
}
