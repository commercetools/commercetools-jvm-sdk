package io.sphere.sdk.products.search;

import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.search.model.MoneyFacetAndFilterSearchModel;
import io.sphere.sdk.search.model.MoneyFacetSearchModel;
import io.sphere.sdk.search.model.SearchModel;
import io.sphere.sdk.search.model.SearchModelImpl;

import javax.annotation.Nullable;

public class ProductVariantFacetAndFilterSearchModel extends SearchModelImpl<ProductProjection> {

    ProductVariantFacetAndFilterSearchModel(@Nullable final SearchModel<ProductProjection> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment);
    }

    public ProductAttributeFacetAndFilterSearchModel attribute() {
        return new ProductAttributeFacetAndFilterSearchModel(this, "attributes");
    }

    public MoneyFacetAndFilterSearchModel<ProductProjection> price() {
        return moneyFacetAndFilterSearchModel("price");
    }
}
