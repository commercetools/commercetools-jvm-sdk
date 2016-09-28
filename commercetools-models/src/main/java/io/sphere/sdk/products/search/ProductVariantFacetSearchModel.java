package io.sphere.sdk.products.search;

import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.search.model.SearchModel;
import io.sphere.sdk.search.model.SearchModelImpl;
import io.sphere.sdk.search.model.MoneyFacetSearchModel;
import io.sphere.sdk.search.model.TermFacetSearchModel;

import javax.annotation.Nullable;

public final class ProductVariantFacetSearchModel extends SearchModelImpl<ProductProjection> {

    ProductVariantFacetSearchModel(@Nullable final SearchModel<ProductProjection> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment);
    }

    public ProductAttributeFacetSearchModel attribute() {
        return new ProductAttributeFacetSearchModel(this, "attributes");
    }

    public MoneyFacetSearchModel<ProductProjection> price() {
        return moneyFacetSearchModel("price");
    }

    public ProductVariantAvailabilityFacetSearchModel<ProductProjection> availability() {
        return new ProductVariantAvailabilityFacetSearchModelImpl<>(this, "availability");
    }

    public TermFacetSearchModel<ProductProjection, Boolean> scopedPriceDiscounted() {
        return booleanSearchModel("scopedPriceDiscounted").faceted();
    }
}
