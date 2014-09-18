package io.sphere.sdk.products;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.Optional;

import static io.sphere.sdk.products.ProductProjectionType.CURRENT;

/**
 * For construction you can use the {@link io.sphere.sdk.products.ProductCatalogDataBuilder}.
 */
@JsonDeserialize(as=ProductCatalogDataImpl.class)
public interface ProductCatalogData {
    boolean isPublished();

    boolean hasStagedChanges();

    Optional<ProductData> getCurrent();

    ProductData getStaged();

    default Optional<ProductData> get(final ProductProjectionType productProjectionType) {
        return productProjectionType == CURRENT ? getCurrent() : Optional.of(getStaged());
    }
}
