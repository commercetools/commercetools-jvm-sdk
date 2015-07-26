package io.sphere.sdk.products.search;

import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.search.MoneySearchModel;
import io.sphere.sdk.search.SearchModel;
import io.sphere.sdk.search.SearchModelImpl;
import io.sphere.sdk.search.SimpleSearchSortDirection;

public class ProductVariantSearchModel extends SearchModelImpl<ProductProjection> {

    ProductVariantSearchModel(final SearchModel<ProductProjection> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    public ProductAttributeSearchModel attribute() {
        return new ProductAttributeSearchModel(this, "attributes");
    }

    public MoneySearchModel<ProductProjection, SimpleSearchSortDirection> price() {
        return new MoneySearchModel<>(this, "price");
    }
}
