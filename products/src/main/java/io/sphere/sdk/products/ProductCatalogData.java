package io.sphere.sdk.products;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import static io.sphere.sdk.products.ProductProjectionType.CURRENT;

/**
 * For construction you can use the {@link io.sphere.sdk.products.ProductCatalogDataBuilder}.
 */
@JsonDeserialize(as=ProductCatalogDataImpl.class)
public interface ProductCatalogData {
    boolean isPublished();

    boolean hasStagedChanges();

    ProductData getCurrent();

    ProductData getStaged();

    default ProductData get(final ProductProjectionType productProjectionType) {
        return productProjectionType == CURRENT ? getCurrent() : getStaged();
    }
}
