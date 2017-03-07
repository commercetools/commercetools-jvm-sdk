package io.sphere.sdk.products.search;

import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.search.model.*;

import javax.annotation.Nullable;

public final class ProductVariantFilterSearchModel extends SearchModelImpl<ProductProjection> {

    ProductVariantFilterSearchModel(@Nullable final SearchModel<ProductProjection> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment);
    }

    public ProductAttributeFilterSearchModel attribute() {
        return new ProductAttributeFilterSearchModel(this, "attributes");
    }

    public ProductVariantAvailabilityFilterSearchModel<ProductProjection> availability() {
        return new ProductVariantAvailabilityFilterSearchModelImpl<>(this, "availability");
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

    /**
     * Creates filters for the scopedPriceDiscounted property of a product variant.
     *
     * {@include.example io.sphere.sdk.products.search.ScopedPriceSearchIntegrationTest#discounts()}
     *
     * @return filters model
     */
    public TermFilterExistsAndMissingSearchModel<ProductProjection, Boolean> scopedPriceDiscounted() {
        return booleanSearchModel("scopedPriceDiscounted").filtered();
    }

    public ExistsAndMissingFilterSearchModelSupport<ProductProjection> prices() {
        return existsAndMissingFilterSearchModelSupport("prices");
    }

    /**
     * Creates filters for the sku property of a product variant.
     *
     * {@include.example io.sphere.sdk.products.search.ProductProjectionSearchFiltersIntegrationTest#filterBySku()}
     *
     * @return filters model
     */
    public TermFilterExistsAndMissingSearchModel<ProductProjection, String> sku() {
        return stringSearchModel("sku").filtered();
    }

    public TermFilterExistsAndMissingSearchModel<ProductProjection, String> key() {
        return stringSearchModel("key").filtered();
    }

}
