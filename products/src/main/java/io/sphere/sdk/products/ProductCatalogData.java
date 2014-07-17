package io.sphere.sdk.products;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * For construction you can use the {@link io.sphere.sdk.products.ProductCatalogDataBuilder}.
 */
@JsonDeserialize(as=ProductCatalogDataImpl.class)
public interface ProductCatalogData {
    boolean isPublished();

    ProductData getCurrent();

    ProductData getStaged();
}
