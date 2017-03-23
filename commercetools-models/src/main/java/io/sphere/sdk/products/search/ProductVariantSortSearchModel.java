package io.sphere.sdk.products.search;

import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.search.model.MultiValueSortSearchModel;
import io.sphere.sdk.search.model.MultiValueSortSearchModelFactory;
import io.sphere.sdk.search.model.SearchModel;
import io.sphere.sdk.search.model.SortableSearchModel;

import javax.annotation.Nullable;

public final class ProductVariantSortSearchModel extends SortableSearchModel<ProductProjection, MultiValueSortSearchModel<ProductProjection>> {

    ProductVariantSortSearchModel(@Nullable final SearchModel<ProductProjection> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment, MultiValueSortSearchModelFactory.of());
    }

    public ProductAttributeSortSearchModel attribute() {
        return new ProductAttributeSortSearchModel(this, "attributes");
    }

    public MultiValueSortSearchModel<ProductProjection> price() {
        return searchModel(/*sic! price is at top level*/null, "price").sorted();
    }

    public ScopedPriceSortSearchModel<ProductProjection> scopedPrice() {
        return new ScopedPriceSortSearchModelImpl<>(this, "scopedPrice");
    }

    public ProductVariantAvailabilitySortSearchModel<ProductProjection> availability() {
        return new ProductVariantAvailabilitySortSearchModelImpl<>(this, "availability");
    }

    public MultiValueSortSearchModel<ProductProjection> scopedPriceDiscounted() {
        return searchModel("scopedPriceDiscounted").sorted();
    }

    public MultiValueSortSearchModel<ProductProjection> sku() {
        return searchModel("sku").sorted();
    }
}
