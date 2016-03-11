package io.sphere.sdk.products.search;

import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.search.model.MoneyFilterSearchModel;
import io.sphere.sdk.search.model.SearchModel;
import io.sphere.sdk.search.model.SearchModelImpl;

import javax.annotation.Nullable;

public final class ProductVariantFilterSearchModel extends SearchModelImpl<ProductProjection> {

    ProductVariantFilterSearchModel(@Nullable final SearchModel<ProductProjection> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment);
    }

    public ProductAttributeFilterSearchModel attribute() {
        return new ProductAttributeFilterSearchModel(this, "attributes");
    }

    public MoneyFilterSearchModel<ProductProjection> price() {
        return moneyFilterSearchModel("price");
    }

    /**
     * Provides filters for scoped prices.
     *
     * <p>Simple example:</p>
     * {@include.example io.sphere.sdk.products.search.ScopedPriceSearchIntegrationTest#filterByValueCentAmountAndCountry()}
     *
     * <p>Example with a discount:</p>
     * {@include.example io.sphere.sdk.products.search.ScopedPriceSearchIntegrationTest#discounts()}
     *
     * @return objects helping to create filters for scoped prices
     */
    public ScopedPriceFilterSearchModel<ProductProjection> scopedPrice() {
        return new ScopedPriceFilterSearchModel<>(this, "scopedPrice");
    }
}
