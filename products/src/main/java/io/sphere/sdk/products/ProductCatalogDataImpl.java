package io.sphere.sdk.products;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.sphere.sdk.models.Base;

class ProductCatalogDataImpl extends Base implements ProductCatalogData {
    @JsonProperty("published")
    private final boolean isPublished;
    private final ProductData current;
    private final ProductData staged;

    @JsonCreator
    ProductCatalogDataImpl(final boolean isPublished, final ProductData current, final ProductData staged) {
        this.isPublished = isPublished;
        this.current = current;
        this.staged = staged;
    }

    public boolean isPublished() {
        return isPublished;
    }

    public ProductData getCurrent() {
        return current;
    }

    public ProductData getStaged() {
        return staged;
    }
}
