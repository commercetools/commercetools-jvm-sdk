package io.sphere.sdk.products;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(as=ProductCatalogDataImpl.class)
public interface ProductCatalogData {
    boolean isPublished();

    ProductData getCurrent();

    ProductData getStaged();
}
