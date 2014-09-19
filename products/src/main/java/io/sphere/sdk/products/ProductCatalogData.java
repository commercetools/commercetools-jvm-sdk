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

    /**
     * The current data of the product.
     *
     * The JSON of the product catalog data always contains a filled current data field, but since it should only be
     * accessed if "published" contains true, this method returns an optional {@link io.sphere.sdk.products.ProductData}.
     *
     * @return The current product data if present.
     */
    Optional<ProductData> getCurrent();

    ProductData getStaged();

    default Optional<ProductData> get(final ProductProjectionType productProjectionType) {
        return productProjectionType == CURRENT ? getCurrent() : Optional.of(getStaged());
    }
}
