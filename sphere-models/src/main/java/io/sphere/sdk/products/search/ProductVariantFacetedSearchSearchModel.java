package io.sphere.sdk.products.search;

import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.search.model.MoneyFacetedSearchSearchModel;
import io.sphere.sdk.search.model.SearchModel;
import io.sphere.sdk.search.model.SearchModelImpl;

import javax.annotation.Nullable;

public final class ProductVariantFacetedSearchSearchModel extends SearchModelImpl<ProductProjection> {

    ProductVariantFacetedSearchSearchModel(@Nullable final SearchModel<ProductProjection> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment);
    }

    public ProductAttributeFacetedSearchSearchModel attribute() {
        return new ProductAttributeFacetedSearchSearchModel(this, "attributes");
    }

    public MoneyFacetedSearchSearchModel<ProductProjection> price() {
        return moneyFacetedSearchSearchModel("price");
    }
}
