package io.sphere.sdk.products.search;

import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.search.*;

import java.util.Optional;

public class ProductVariantSearchModel extends SearchModelImpl<ProductProjection> {

    ProductVariantSearchModel(final Optional<? extends SearchModel<ProductProjection>> parent, final Optional<String> pathSegment) {
        super(parent, pathSegment);
    }

    ProductVariantSearchModel(Optional<? extends SearchModel<ProductProjection>> parent, final String pathSegment) {
        this(parent, Optional.of(pathSegment));
    }

    public ProductAttributeSearchModel attribute() {
        return new ProductAttributeSearchModel(Optional.of(this), "attributes");
    }

    public MoneySearchModel<ProductProjection, SimpleSearchSortDirection> price() {
        return new MoneySearchModel<>(Optional.of(this), "price");
    }
}
