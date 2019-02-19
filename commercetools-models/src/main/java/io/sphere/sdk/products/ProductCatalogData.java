package io.sphere.sdk.products;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
     * The JSON of the product catalog data always contains a filled current data field, but it should only be
     * accessed if "published" contains true.
     *
     * @see ProductCatalogData#getCurrentUnsafe() to access current data even if product catalog is not "published"
     *
     * @return The current product data if it is "published", otherwise null.
     */
    @Nullable
    ProductData getCurrent();

    /**
     * The current data of the product.
     *
     * The JSON of the product catalog data always contains a filled current data field, but it should only be
     * accessed if "published" contains true.
     *
     * However, this method allows for current data access even if product catalog is not "published".
     *
     * @see ProductCatalogData#getCurrent() to access current data in a safer way
     *
     * @return The current product data
     */
    @JsonIgnore
    @Nullable
    ProductData getCurrentUnsafe();

    ProductData getStaged();

    @Nullable
    default ProductData get(final ProductProjectionType productProjectionType) {
        return productProjectionType == CURRENT ? getCurrent() : getStaged();
    }
}
