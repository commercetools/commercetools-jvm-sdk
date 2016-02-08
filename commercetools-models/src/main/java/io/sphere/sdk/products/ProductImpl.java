package io.sphere.sdk.products;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.time.ZonedDateTime;
import java.util.Optional;
import io.sphere.sdk.models.ResourceImpl;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.reviews.ReviewRatingStatistics;
import io.sphere.sdk.states.State;
import io.sphere.sdk.taxcategories.TaxCategory;

import javax.annotation.Nullable;

class ProductImpl extends ResourceImpl<Product> implements Product {
    private final Reference<ProductType> productType;
    private final ProductCatalogData masterData;
    @Nullable
    private final Reference<TaxCategory> taxCategory;
    @Nullable
    private final Reference<State> state;
    @Nullable
    private final ReviewRatingStatistics reviewRatingStatistics;

    @JsonCreator
    ProductImpl(final String id, final Long version, final ZonedDateTime createdAt, final ZonedDateTime lastModifiedAt,
                @Nullable final Reference<ProductType> productType, final ProductCatalogData masterData,
                @Nullable final Reference<TaxCategory> taxCategory, @Nullable final Reference<State> state,
                @Nullable final ReviewRatingStatistics reviewRatingStatistics) {
        super(id, version, createdAt, lastModifiedAt);
        this.productType = productType;
        this.masterData = masterData;
        this.taxCategory = taxCategory;
        this.state = state;
        this.reviewRatingStatistics = reviewRatingStatistics;
        Optional.of(masterData)
                .filter(d -> d instanceof ProductCatalogDataImpl)
                .ifPresent(d -> ((ProductCatalogDataImpl)d).setProductId(id));
    }

    @Override
    public Reference<ProductType> getProductType() {
        return productType;
    }

    @Override
    public ProductCatalogData getMasterData() {
        return masterData;
    }

    @Nullable
    @Override
    public Reference<TaxCategory> getTaxCategory() {
        return taxCategory;
    }

    @Override
    @Nullable
    public Reference<State> getState() {
        return state;
    }

    @Override
    @Nullable
    public ReviewRatingStatistics getReviewRatingStatistics() {
        return reviewRatingStatistics;
    }
}
