package io.sphere.sdk.products;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.reviews.ReviewRatingStatistics;
import io.sphere.sdk.states.State;
import io.sphere.sdk.taxcategories.TaxCategory;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;
import java.util.Optional;

final class ProductImpl extends ProductImplBase {

    @JsonCreator
    ProductImpl(final ZonedDateTime createdAt, final String id, @Nullable final String key,
                    final ZonedDateTime lastModifiedAt, final ProductCatalogData masterData,
                    final Reference<ProductType> productType,
                    @Nullable final ReviewRatingStatistics reviewRatingStatistics,
                    @Nullable final Reference<State> state, @Nullable final Reference<TaxCategory> taxCategory,
                    final Long version) {
        super(createdAt, id, key, lastModifiedAt, masterData, productType, reviewRatingStatistics, state, taxCategory, version);
        Optional.of(masterData).filter(d -> d instanceof ProductCatalogDataImpl).ifPresent(d -> ((ProductCatalogDataImpl)d).setProductId(id));
    }
}
