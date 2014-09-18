package io.sphere.sdk.products;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.sphere.sdk.models.Base;

import java.util.Optional;

class ProductCatalogDataImpl extends Base implements ProductCatalogData {
    @JsonProperty("published")
    private final boolean isPublished;
    @JsonProperty("hasStagedChanges")
    private final boolean hasStagedChanges;
    private final ProductData current;
    private final ProductData staged;

    @JsonCreator
    ProductCatalogDataImpl(final boolean isPublished, final ProductData current, final ProductData staged,
                           final boolean hasStagedChanges) {
        this.isPublished = isPublished;
        this.current = current;
        this.staged = staged;
        this.hasStagedChanges = hasStagedChanges;
    }

    public boolean isPublished() {
        return isPublished;
    }

    public Optional<ProductData> getCurrent() {
        return isPublished ? Optional.<ProductData>of(current) : Optional.<ProductData>empty();
    }

    public ProductData getStaged() {
        return staged;
    }

    public boolean hasStagedChanges() {
        return hasStagedChanges;
    }
}
