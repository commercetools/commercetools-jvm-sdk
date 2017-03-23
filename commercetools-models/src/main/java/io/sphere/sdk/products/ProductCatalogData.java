package io.sphere.sdk.products;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.ResourceValue;

import javax.annotation.Nullable;

import static io.sphere.sdk.products.ProductProjectionType.CURRENT;

@ResourceValue(abstractResourceClass = true)
@JsonDeserialize(as=ProductCatalogDataImpl.class)
public interface ProductCatalogData {
    @JsonProperty("published")
    Boolean isPublished();

    @JsonProperty("hasStagedChanges")
    Boolean hasStagedChanges();

    /**
     * The current data of the product.
     *
     * The JSON of the product catalog data always contains a filled current data field, but since it should only be
     * accessed if "published" contains true, this method returns an optional {@link io.sphere.sdk.products.ProductData}.
     *
     * @return The current product data if present.
     */
    @Nullable
    ProductData getCurrent();

    ProductData getStaged();

    @Nullable
    default ProductData get(final ProductProjectionType productProjectionType) {
        return productProjectionType == CURRENT ? getCurrent() : getStaged();
    }
}
